package product.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PromotionFactory {

    public static Promotion createPromotionFactory(String clientInput) {
        String promotionNumber = null;
        if (clientInput.contains("off")) {
            promotionNumber = clientInput.replaceAll("[^\\d]", "");
            clientInput = "off";
        }
        switch (clientInput) {
            case "none":
                return new DiscountPromotion(BigDecimal.valueOf(0));
            case "Buy 2, get 3rd free":
                return new BuyAmountGetAmountForFreePromotion(2, 1);
            case "off":
                return new DiscountPromotion(new BigDecimal(promotionNumber).divide(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP));
            default:
                throw new IllegalArgumentException("Invalid product type: " + clientInput);
        }
    }
}
