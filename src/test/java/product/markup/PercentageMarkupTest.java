package product.markup;

import org.junit.jupiter.api.Test;
import product.markup.PercentageMarkup;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PercentageMarkupTest {

    @Test
    public void testCalculateStandardPriceUnit() {
        PercentageMarkup markup = new PercentageMarkup(BigDecimal.valueOf(15));

        BigDecimal unitCost = BigDecimal.valueOf(0.88);
        BigDecimal expectedStandardUnitPrice = BigDecimal.valueOf(1.01);

        assertEquals(expectedStandardUnitPrice, markup.calculateStandardPriceUnit(unitCost));
    }

}