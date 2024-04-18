package product.markup;

import java.math.BigDecimal;
public interface Markup {
    public int DECIMAL_PLACES = 2;
    public int DECIMAL_PLACES_USING_FRACTION = 4;
    public BigDecimal HUNDRED = BigDecimal.valueOf(100);
    public BigDecimal calculateStandardPriceUnit(BigDecimal unitCost);

    public BigDecimal getMarkup();

}
