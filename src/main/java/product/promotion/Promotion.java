package product.promotion;

import java.math.BigDecimal;

public interface Promotion {
    public int DECIMALS = 5;
    public int DECIMALS_USING_FRACTION = 4;

    public BigDecimal calculatePromotionalUnitPrice(BigDecimal standardUnitPrice);

    public BigDecimal getDiscount();
}
