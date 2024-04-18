package product;

import static org.junit.jupiter.api.Assertions.*;
import product.markup.EURMarkup;
import product.markup.PercentageMarkup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import product.Product;
import product.promotion.BuyAmountGetAmountForFreePromotion;
import product.promotion.DiscountPromotion;

import java.math.BigDecimal;

public class ProductTest {
    private static Product product1, product2, product3, product4;

    @BeforeAll
    public static void setUp() {
        product1 = new Product(1, "Danish Muffin", BigDecimal.valueOf(0.52), new PercentageMarkup(BigDecimal.valueOf(80)), new DiscountPromotion(BigDecimal.valueOf(0)));
        product2 = new Product(2, "Granny’s Cup Cake", BigDecimal.valueOf(0.38), new PercentageMarkup(BigDecimal.valueOf(120)), new DiscountPromotion(new BigDecimal(0.3)));
        product3 = new Product(3, "Frenchy’s Croissant", BigDecimal.valueOf(0.41), new EURMarkup(BigDecimal.valueOf(0.90)), new DiscountPromotion(BigDecimal.valueOf(0)));
        product4 = new Product(4, "Crispy chips", BigDecimal.valueOf(0.60), new EURMarkup(BigDecimal.valueOf(1.00)), new BuyAmountGetAmountForFreePromotion(2,1));
    }

    @Test
    public void testGetStandardUnitPrice() {

        assertAll(
            () -> assertEquals("0.94", product1.getStandardUnitPrice().toString()),
            () -> assertEquals("0.84", product2.getStandardUnitPrice().toString()),
            () -> assertEquals("1.31", product3.getStandardUnitPrice().toString()),
            () -> assertEquals("1.60", product4.getStandardUnitPrice().toString())
        );
    }

    @Test
    public void testGetPromotionalUnitPrice() {

        assertAll(
            () -> assertEquals("-1", product1.getPromotionalUnitPrice().toString()),
            () -> assertEquals("0.58800", product2.getPromotionalUnitPrice().toString()),
            () -> assertEquals("-1", product3.getPromotionalUnitPrice().toString()),
            () -> assertEquals("1.06672", product4.getPromotionalUnitPrice().toString())
        );
    }




}
