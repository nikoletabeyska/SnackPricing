package product.promotion;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PromotionFactoryTest {

    @Test
    public void testNonePromotion() {
        Promotion promotion = PromotionFactory.createPromotionFactory("none");
        assertEquals(BigDecimal.valueOf(0.0000).setScale(4), promotion.getDiscount());
    }

    @Test
    public void testOffPromotion() {
        Promotion promotion = PromotionFactory.createPromotionFactory("34.4% off");
        assertEquals(BigDecimal.valueOf(0.3440).setScale(4), promotion.getDiscount());
    }

    @Test
    public void testBuyAmountGetAmountForFreePromotion() {
        Promotion promotion = PromotionFactory.createPromotionFactory("Buy 2, get 3rd free");
        assertEquals(BigDecimal.valueOf(0.3333).setScale(4), promotion.getDiscount());
    }
}