package product.markup;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EURMarkupTest {
    @Test
    public void testCalculateStandardPriceUnit() {
        EURMarkup markup = new EURMarkup(BigDecimal.valueOf(1.22));

        BigDecimal unitCost = BigDecimal.valueOf(0.37);
        BigDecimal expectedStandardUnitPrice = BigDecimal.valueOf(1.59);

        assertEquals(expectedStandardUnitPrice, markup.calculateStandardPriceUnit(unitCost));
    }
}
