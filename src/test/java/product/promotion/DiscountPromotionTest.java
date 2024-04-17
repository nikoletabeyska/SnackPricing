package product.promotion;

import org.junit.jupiter.api.Test;
import product.promotion.DiscountPromotion;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DiscountPromotionTest {

    @Test
    public void testCalculatePromotionalUnitPrice() {
        DiscountPromotion discountPromotion = new DiscountPromotion(BigDecimal.valueOf(0.60));
        DiscountPromotion discountPromotion2 = new DiscountPromotion(BigDecimal.valueOf(0.555555));

        BigDecimal standardPrice = BigDecimal.valueOf(1.66);
        BigDecimal standardPrice2 = BigDecimal.valueOf(0.98);
        BigDecimal expectedPromotionalUnitPrice =  BigDecimal.valueOf(0.66400).setScale(5);
        BigDecimal expectedPromotionalUnitPrice2 =  BigDecimal.valueOf(0.43551);

        assertEquals(expectedPromotionalUnitPrice, discountPromotion.calculatePromotionalUnitPrice(standardPrice));
        assertEquals(expectedPromotionalUnitPrice2, discountPromotion2.calculatePromotionalUnitPrice(standardPrice2));
    }

}