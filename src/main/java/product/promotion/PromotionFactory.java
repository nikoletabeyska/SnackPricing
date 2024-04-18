package product.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PromotionFactory {

    public static final int DECIMALS_USING_FRACTION = 4;
    public static Promotion createPromotionFactory(String userInput) {
        String promotionNumber = null;
        if (userInput.contains("off")) {
            promotionNumber = userInput.split(" ")[0].replace("%", "");
            userInput = "off";
        }
        switch (userInput) {
            case "none":
                return new DiscountPromotion(BigDecimal.valueOf(0));
            case "Buy 2, get 3rd free":
                return new BuyAmountGetAmountForFreePromotion(2, 1);
            case "off":
                BigDecimal number = new BigDecimal(promotionNumber).setScale(DECIMALS_USING_FRACTION, RoundingMode.HALF_UP);
                return new DiscountPromotion(number.divide(BigDecimal.valueOf(100)));

            default:
                throw new IllegalArgumentException("Invalid product type: " + userInput);
        }
    }
}
