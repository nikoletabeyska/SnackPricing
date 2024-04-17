import client.Client;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderTest {
    private static Order order;
    private static Client mockClient;
    private static Product mockProduct1, mockProduct2;
    public static final BigDecimal NONE = BigDecimal.valueOf(-1);


    @BeforeAll
    public static void setUp() {
        mockClient = mock(Client.class);
        mockProduct1 = mock(Product.class);
        mockProduct2 = mock(Product.class);
        HashMap<Product, Integer> orderedProducts = new HashMap<>();
        orderedProducts.put(mockProduct1, 10000);
        orderedProducts.put(mockProduct2,20000);
        order = new Order(mockClient, orderedProducts);
    }

    @Test
    void testPrintOrderInfoClientDetails() {
        when(mockProduct1.getStandardUnitPrice()).thenReturn(BigDecimal.valueOf(0.94));
        when(mockProduct1.getStandardUnitPrice()).thenReturn(BigDecimal.valueOf(1.60));
        when(mockProduct1.getPromotionalUnitPrice()).thenReturn(BigDecimal.valueOf(-1));
        when(mockProduct1.getPromotionalUnitPrice()).thenReturn(BigDecimal.valueOf(1.06672));



    }

    @Test
    void testGetClientInfo() {
        when(mockClient.getName()).thenReturn("MNO Vending");
        String expectedOutput = "\nClient:      MNO Vending\n\n";
        assertEquals(expectedOutput, order.getClientInfo());
    }

    @Test
    void testCalculateLineTotal() {
        BigDecimal standardUnitPrice1 = BigDecimal.valueOf(0.94);
        BigDecimal promotionalUnitPrice1 = BigDecimal.valueOf(-1);
        BigDecimal standardUnitPrice2 = BigDecimal.valueOf(1.60);
        BigDecimal promotionalUnitPrice2 = BigDecimal.valueOf(1.06672);
        BigDecimal quantity1 = BigDecimal.valueOf(10000);
        BigDecimal quantity2 = BigDecimal.valueOf(20000);

        BigDecimal lineTotal1 = order.calculateLineTotal(standardUnitPrice1, promotionalUnitPrice1, quantity1);
        BigDecimal lineTotal2 = order.calculateLineTotal(standardUnitPrice2, promotionalUnitPrice2, quantity2);

        BigDecimal expected1 = BigDecimal.valueOf(9400.00).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expected2 = BigDecimal.valueOf(21334.40).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expected1, lineTotal1);
        assertEquals(expected2, lineTotal2);
    }

    @Test
    public void testGetReadableClientDiscountsAndTotal_NoDiscounts() {
        BigDecimal totalBeforeClientDiscounts = BigDecimal.valueOf(2134.43);
        when(mockClient.calculateBasicClientDiscount(any())).thenReturn(NONE);
        when(mockClient.calculateAdditionalVolumeDiscount(any())).thenReturn(Pair.of(NONE,NONE));
        String expectedOutput = "Order Total Amount:                    EUR 2134.43\n";
        assertEquals(expectedOutput, order.getReadableClientDiscountsAndTotal(totalBeforeClientDiscounts));
    }
    @Test
    public void testGetReadableClientDiscountsAndTotal_BasicClientDiscount() {
        BigDecimal totalBeforeClientDiscounts = BigDecimal.valueOf(2134.43);
        when(mockClient.calculateBasicClientDiscount(any())).thenReturn(BigDecimal.valueOf(112.34));
        when(mockClient.getBasicClientDiscountReadableFormat()).thenReturn("3%");
        when(mockClient.calculateAdditionalVolumeDiscount(any())).thenReturn(Pair.of(NONE,NONE));
        String expectedOutput = "Basic Client Discount at 3%:           EUR 112.34\n" +
            "Order Total Amount:                    EUR 2022.09\n";
        assertEquals(expectedOutput, order.getReadableClientDiscountsAndTotal(totalBeforeClientDiscounts));
    }

    @Test
    public void testGetReadableClientDiscountsAndTotal_AdditionalVolumeDiscount() {
        BigDecimal totalBeforeClientDiscounts = BigDecimal.valueOf(30734.40).setScale(2,RoundingMode.HALF_UP);
        when(mockClient.calculateBasicClientDiscount(any())).thenReturn(NONE);
        when(mockClient.calculateAdditionalVolumeDiscount(any())).thenReturn(Pair.of(BigDecimal.valueOf(7), BigDecimal.valueOf(2151.41)));
        String expectedOutput = "Additional Volume Discount at 7%:      EUR 2151.41\n" +
            "Order Total Amount:                    EUR 28582.99\n";
        assertEquals(expectedOutput, order.getReadableClientDiscountsAndTotal(totalBeforeClientDiscounts));
    }

    @Test
    public void testGetReadableClientDiscountsAndTotal_BasicClientAndAdditionalVolumeDiscount() {
        BigDecimal totalBeforeClientDiscounts = BigDecimal.valueOf(30734.40).setScale(2,RoundingMode.HALF_UP);
        when(mockClient.calculateBasicClientDiscount(any())).thenReturn(BigDecimal.valueOf(922.03));
        when(mockClient.getBasicClientDiscountReadableFormat()).thenReturn("3%");
        when(mockClient.calculateAdditionalVolumeDiscount(any())).thenReturn(Pair.of(BigDecimal.valueOf(5), BigDecimal.valueOf(1490.62)));
        String expectedOutput = "Basic Client Discount at 3%:           EUR 922.03\n" +
            "Additional Volume Discount at 5%:      EUR 1490.62\n" +
            "Order Total Amount:                    EUR 28321.75\n";
        assertEquals(expectedOutput, order.getReadableClientDiscountsAndTotal(totalBeforeClientDiscounts));
    }

    @Test
    void testCalculateAndPrintBasicClientDiscountWhenNone() {
        when(mockClient.calculateBasicClientDiscount(any())).thenReturn(NONE);
        assertEquals(null, order.calculateAndPrintBasicClientDiscount(null, null));
    }

    @Test
    void testCalculateAndPrintAdditionalVolumeClientDiscountWhenNone() {
        when(mockClient.calculateAdditionalVolumeDiscount(any())).thenReturn(Pair.of(NONE,NONE));
        assertEquals(null, order.calculateAndPrintAdditionalVolumeClientDiscount(null, null));
    }
}