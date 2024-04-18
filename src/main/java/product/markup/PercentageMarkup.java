package product.markup;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageMarkup implements Markup {

    private BigDecimal fractionMarkup;

    public PercentageMarkup(BigDecimal percentageMarkup) {
        //33.33% -> 0.3333
        this.fractionMarkup = percentageMarkup.divide(HUNDRED, DECIMAL_PLACES_USING_FRACTION, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateStandardPriceUnit(BigDecimal unitCost) {
        BigDecimal standardUnitPrice = unitCost.add(unitCost.multiply(fractionMarkup))
            .setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        return standardUnitPrice;
    }

    @Override
    public BigDecimal getMarkup() {
        return fractionMarkup;
    }

    @Override
    public String toString() {
        return "PercentageMarkup{" +
            "fractionMarkup=" + fractionMarkup +
            '}';
    }

}
