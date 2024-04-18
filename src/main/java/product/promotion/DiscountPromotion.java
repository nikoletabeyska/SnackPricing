package product.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountPromotion implements Promotion {
    public BigDecimal discount;
    public static final BigDecimal NONE = BigDecimal.valueOf(-1);
    public DiscountPromotion(BigDecimal discount) {
        this.discount = discount.setScale(DECIMALS_USING_FRACTION, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculatePromotionalUnitPrice(BigDecimal standardUnitPrice) {
        if (discount.compareTo(BigDecimal.valueOf(0)) == 0) return NONE;
        BigDecimal promotionalUnitPrice = standardUnitPrice.subtract(standardUnitPrice.multiply(discount))
            .setScale(DECIMALS, RoundingMode.HALF_UP);
        return promotionalUnitPrice;

    }

    @Override
    public BigDecimal getDiscount() {
        return discount;
    }

}
