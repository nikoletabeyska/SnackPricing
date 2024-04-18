package product.markup;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EURMarkup implements Markup {
    private BigDecimal eurMarkup;
    public EURMarkup(BigDecimal eurMarkup) {
        this.eurMarkup = eurMarkup;
    }

    @Override
    public BigDecimal calculateStandardPriceUnit(BigDecimal unitCost) {
        return unitCost.add(eurMarkup).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getMarkup() {
        return eurMarkup;
    }


    @Override
    public String toString() {
        return "EURMarkup{" +
            "eurMarkup=" + eurMarkup +
            '}';
    }
}
