package product.promotion;

import org.junit.jupiter.api.Test;
import product.promotion.BuyAmountGetAmountForFreePromotion;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BuyAmountGetAmountForFreePromotionTest {
    @Test
    public void testCalculatePromotionalUnitPrice() {
        BuyAmountGetAmountForFreePromotion promotion = new BuyAmountGetAmountForFreePromotion(2,1);
        BuyAmountGetAmountForFreePromotion promotion2 = new BuyAmountGetAmountForFreePromotion(3,1);

        BigDecimal standardPrice = BigDecimal.valueOf(1.60);
        BigDecimal standardPrice2 = BigDecimal.valueOf(1.07);

        BigDecimal expectedPromotionalUnitPrice =  BigDecimal.valueOf(1.06672);
        BigDecimal expectedPromotionalUnitPrice2 =  BigDecimal.valueOf(0.80250).setScale(5);


        assertEquals(expectedPromotionalUnitPrice, promotion.calculatePromotionalUnitPrice(standardPrice));
        assertEquals(expectedPromotionalUnitPrice2, promotion2.calculatePromotionalUnitPrice(standardPrice2));

    }
}