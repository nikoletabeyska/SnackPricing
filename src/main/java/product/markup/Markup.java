package product.markup;

import java.math.BigDecimal;
public interface Markup {
    public static final int DECIMAL_PLACES = 2;
    public static final int DECIMAL_PLACES_USING_FRACTION = 4;
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    public BigDecimal calculateStandardPriceUnit(BigDecimal unitCost);

}
