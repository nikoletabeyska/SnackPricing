package product.promotion;

import java.math.BigDecimal;

public interface Promotion {
    public static final int DECIMALS = 5;
    public static final int DECIMALS_USING_FRACTION = 4;

    public BigDecimal calculatePromotionalUnitPrice(BigDecimal standardUnitPrice);
}
