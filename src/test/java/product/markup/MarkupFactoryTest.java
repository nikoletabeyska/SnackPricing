package product.markup;

import org.junit.jupiter.api.Test;
import product.promotion.Promotion;
import product.promotion.PromotionFactory;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MarkupFactoryTest {
    @Test
    public void testPercentageMarkup() {
        Markup markup = MarkupFactory.createMarkupFactory("120%");
        assertEquals(BigDecimal.valueOf(1.2000).setScale(4), markup.getMarkup());
    }

    @Test
    public void testEURMarkup() {
        Markup markup = MarkupFactory.createMarkupFactory("1.00 EUR/unit");
        assertEquals(BigDecimal.valueOf(1.00).setScale(2), markup.getMarkup());
    }


}