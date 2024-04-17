import client.Client;
import org.apache.commons.lang3.tuple.Pair;
import product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private final Client client;
    private final HashMap<Product, Integer> orderedProducts;
    public static final int DECIMAL_PLACES = 2;
    public static final BigDecimal NONE = BigDecimal.valueOf(-1);

    public Order(Client client, HashMap<Product, Integer> orderedProducts) {
        this.client = client;
        this.orderedProducts = orderedProducts;
    }

    public String printOrderInfo() {
        StringBuilder infoTable = new StringBuilder();
        infoTable.append(getClientInfo());
        infoTable.append("Product                 Quantity           Standard Unit Price" +
            "         Promotional Unit Price         Line Total\n\n");
        Product product;
        Integer quantity;
        BigDecimal totalBeforeClientDiscounts = BigDecimal.valueOf(0);

        for (Map.Entry<Product, Integer> productEntry : orderedProducts.entrySet()) {
            product = productEntry.getKey();
            quantity = productEntry.getValue();
            BigDecimal standardUnitPrice = product.getStandardUnitPrice();
            BigDecimal promotionalUnitPrice = product.getPromotionalUnitPrice();
            BigDecimal quantityDecimal = BigDecimal.valueOf(quantity);
            BigDecimal lineTotal = calculateLineTotal(standardUnitPrice, promotionalUnitPrice, quantityDecimal);

            totalBeforeClientDiscounts = totalBeforeClientDiscounts.add(lineTotal);
            infoTable.append(String.format("%-25s %-20d EUR %-20s", product.getName(),
                                                  quantity, standardUnitPrice.toString()));
            if (promotionalUnitPrice.compareTo(NONE) != 0) {
                infoTable.append(String.format("EUR %-26s", promotionalUnitPrice));
            } else {
                infoTable.append(String.format("%-30s", " "));
            }
            infoTable.append(String.format("EUR %-30s\n", lineTotal.toString()));
        }

        infoTable.append(String.format("\nTotal Before Client Discounts:         EUR %s\n", totalBeforeClientDiscounts));
        infoTable.append(getReadableClientDiscountsAndTotal(totalBeforeClientDiscounts));

        return infoTable.toString();
    }

    public String getClientInfo() {
        return "\nClient:      " + client.getName() + "\n\n";
    }

    public BigDecimal calculateLineTotal(BigDecimal standardUnitPrice, BigDecimal promotionalUnitPrice, BigDecimal quantityDecimal) {
        if (promotionalUnitPrice.compareTo(NONE) == 0) {
            return standardUnitPrice.multiply(quantityDecimal);
        } else {
            return promotionalUnitPrice.multiply(quantityDecimal).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        }
    }

    public String getReadableClientDiscountsAndTotal(BigDecimal totalBeforeClientDiscounts) {
        BigDecimal totalAmount = totalBeforeClientDiscounts;
        StringBuilder discountsInfo = new StringBuilder();
        BigDecimal basicClientDiscount = calculateAndPrintBasicClientDiscount(totalBeforeClientDiscounts, discountsInfo);
        if (basicClientDiscount != null) {
            totalAmount = totalAmount.subtract(basicClientDiscount);
        }
        BigDecimal additionalVolumeDiscount = calculateAndPrintAdditionalVolumeClientDiscount(totalAmount, discountsInfo);
        if (additionalVolumeDiscount != null) {
            totalAmount = totalAmount.subtract(additionalVolumeDiscount);
        }

        discountsInfo.append(String.format("Order Total Amount:                    EUR %s\n", totalAmount.toString()));
        return discountsInfo.toString();

    }

    public BigDecimal calculateAndPrintBasicClientDiscount(BigDecimal totalBeforeClientDiscounts, StringBuilder discountsInfo) {
        BigDecimal basicClientDiscount = client.calculateBasicClientDiscount(totalBeforeClientDiscounts);
        if (basicClientDiscount.compareTo(NONE) != 0) {
            discountsInfo.append(String.format("Basic Client Discount at %s:           EUR %s\n",
                client.getBasicClientDiscountReadableFormat(), basicClientDiscount));
            return basicClientDiscount;
        }
        return null;
    }

    public BigDecimal calculateAndPrintAdditionalVolumeClientDiscount(BigDecimal totalAmountAfterBasicClientDiscount, StringBuilder discountsInfo) {
        Pair<BigDecimal, BigDecimal> additionalVolumeDiscount = client.calculateAdditionalVolumeDiscount(totalAmountAfterBasicClientDiscount);
        BigDecimal percentageDiscount = additionalVolumeDiscount.getLeft();
        if (percentageDiscount.compareTo(NONE) != 0) {
            BigDecimal additionalVolumeDiscountAmount = additionalVolumeDiscount.getRight();
            discountsInfo.append(String.format("Additional Volume Discount at %s%%:      EUR %s\n", percentageDiscount,
                additionalVolumeDiscountAmount.toString()));
            return additionalVolumeDiscountAmount;
        }
        return null;
    }

}
