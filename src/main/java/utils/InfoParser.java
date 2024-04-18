package utils;

import client.Client;
import product.Product;
import product.markup.Markup;
import product.markup.MarkupFactory;
import org.json.simple.JSONObject;
import product.promotion.Promotion;
import product.promotion.PromotionFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;


public class InfoParser {
    private static int DECIMAL_PLACES = 2;
    public static BigDecimal parsePercentage(String percentage) {
        if (percentage == null || percentage.isEmpty()) {
            throw new IllegalArgumentException("Percentage string must not be null or empty.");
        }
        return new BigDecimal(percentage.replace("%", "")).divide(BigDecimal.valueOf(100)).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    public static BigDecimal parseThreshold(String threshold) {
        if (threshold == null || threshold.isEmpty()) {
            throw new IllegalArgumentException("Threshold string must not be null or empty.");
        }
        return new BigDecimal(threshold.replace("Above EUR ", "").replace(",", "")).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    public static TreeMap<BigDecimal, BigDecimal> parseVolumeDiscount(JSONObject additionalVolumeDiscount) {
        TreeMap<BigDecimal, BigDecimal> additionalVolumeDiscountAttribute = new TreeMap<>();
        for (Object key : additionalVolumeDiscount.keySet()) {
            String keyField = (String) key;
            BigDecimal keyThreshold = parseThreshold(keyField);
            BigDecimal valueDiscount = parsePercentage((String) additionalVolumeDiscount.get(key));
            additionalVolumeDiscountAttribute.put(keyThreshold, valueDiscount);
        }
        return additionalVolumeDiscountAttribute;
    }
    public static Client parseClient(JSONObject clientJson) {
        int id = Integer.parseInt((String) clientJson.get("Client ID"));
        String name = (String) clientJson.get("Client name");
        BigDecimal basicClientDiscount = parsePercentage((String) clientJson.get("Basic Client Discount"));
        TreeMap<BigDecimal, BigDecimal> additionalVolumeDiscount = parseVolumeDiscount((JSONObject) clientJson.get("Additional Volume Discount"));
        return new Client(id, name, basicClientDiscount, additionalVolumeDiscount);
    }

    public static Product parseProduct(JSONObject productJson) {
        int id = Integer.parseInt((String) productJson.get("Product ID"));
        String name = (String) productJson.get("Product name");
        BigDecimal unitCost = new BigDecimal((String) productJson.get("Unit Cost (EUR)"));

        String markup = ((String) productJson.get("Markup"));
        Markup markupObject = MarkupFactory.createMarkupFactory(markup);

        String promotion = ((String) productJson.get("Product Promotion"));
        Promotion promotionObject = PromotionFactory.createPromotionFactory(promotion);

        return new Product(id, name, unitCost, markupObject, promotionObject);
    }
}
