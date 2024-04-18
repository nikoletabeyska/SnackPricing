package product.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BuyAmountGetAmountForFreePromotion implements Promotion {
    private int amountToBuy;
    private int amountToGetForFree;

    public BuyAmountGetAmountForFreePromotion(int amountToBuy, int amountToGetForFree) {
        this.amountToBuy = amountToBuy;
        this.amountToGetForFree = amountToGetForFree;
    }


    @Override
    public BigDecimal calculatePromotionalUnitPrice(BigDecimal standardUnitPrice) {
        BigDecimal totalAmountGot = BigDecimal.valueOf(amountToBuy + amountToGetForFree);
        BigDecimal amountToGetForFree = BigDecimal.valueOf(this.amountToGetForFree);

        // 33.33% is 0.3333 - 4 decimals
        BigDecimal fractionDiscount = amountToGetForFree.divide(totalAmountGot, DECIMALS_USING_FRACTION, RoundingMode.HALF_UP);
        BigDecimal priceAfterFractionDiscount = standardUnitPrice.subtract(standardUnitPrice.multiply(fractionDiscount).setScale(DECIMALS, RoundingMode.HALF_UP)).setScale(DECIMALS, RoundingMode.HALF_UP);

        return priceAfterFractionDiscount;
    }

    @Override
    public BigDecimal getDiscount() {
        BigDecimal totalAmountGot = BigDecimal.valueOf(amountToBuy + amountToGetForFree);
        BigDecimal amountToGetForFree = BigDecimal.valueOf(this.amountToGetForFree);
        return amountToGetForFree.divide(totalAmountGot, DECIMALS_USING_FRACTION, RoundingMode.HALF_UP);
    }
}
