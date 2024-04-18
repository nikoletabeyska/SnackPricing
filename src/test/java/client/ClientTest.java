package client;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {

    private static Client client1, client2;
    public static final BigDecimal NONE = BigDecimal.valueOf(-1);

    @BeforeAll
    public static void setUp() {
        TreeMap<BigDecimal, BigDecimal> additionalAmountDiscount1 = new TreeMap<>();
        additionalAmountDiscount1.put(BigDecimal.valueOf(10000.00), BigDecimal.valueOf(0.0500));
        additionalAmountDiscount1.put(BigDecimal.valueOf(30000.00),BigDecimal.valueOf(0.0700));

        client1 = new Client(1, "TestName1", BigDecimal.valueOf(0),additionalAmountDiscount1);

        TreeMap<BigDecimal, BigDecimal> additionalAmountDiscount2 = new TreeMap<>();
        additionalAmountDiscount2.put(BigDecimal.valueOf(10000.00),BigDecimal.valueOf(0.0000));
        additionalAmountDiscount2.put(BigDecimal.valueOf(30000.00),BigDecimal.valueOf(0.0200));

        client2= new Client(2, "TestName2", BigDecimal.valueOf(0.0500),additionalAmountDiscount2);

    }

    @Test
    public void testCalculateBasicClientDiscount_NoDiscount() {
        BigDecimal totalPrice1 = BigDecimal.valueOf(30734.40);
        BigDecimal result1 = client1.calculateBasicClientDiscount(totalPrice1);

        assertEquals(NONE,result1);
    }

    @Test
    public void testCalculateBasicClientDiscount_HasDiscount() {
        BigDecimal totalPrice1 = BigDecimal.valueOf(2199.89);
        BigDecimal result1 = client2.calculateBasicClientDiscount(totalPrice1);
        BigDecimal expected1 = BigDecimal.valueOf(109.99) ;

        assertEquals(expected1,result1);

    }

    @Test
    public void testCalculateAdditionalVolumeDiscount_NoDiscount() {
       BigDecimal totalPrice1 = BigDecimal.valueOf(10000.00);
       BigDecimal totalPrice2 = BigDecimal.valueOf(9999.99);

       Pair<BigDecimal, BigDecimal> result1 = client1.calculateAdditionalVolumeDiscount(totalPrice1);
       Pair<BigDecimal, BigDecimal> result2 = client1.calculateAdditionalVolumeDiscount(totalPrice2);

        assertEquals(Pair.of(NONE, null), result1);
        assertEquals(Pair.of(NONE, null), result2);
    }

    @Test
    public void testCalculateAdditionalVolumeDiscount_HasDiscount() {
        BigDecimal totalPrice1 = BigDecimal.valueOf(10000.01);
        BigDecimal totalPrice2 = BigDecimal.valueOf(30000.01);
        BigDecimal totalPrice3 = BigDecimal.valueOf(30734.40);

        Pair<BigDecimal, BigDecimal> result1 = client1.calculateAdditionalVolumeDiscount(totalPrice1);
        Pair<BigDecimal, BigDecimal> result2 = client1.calculateAdditionalVolumeDiscount(totalPrice2);
        Pair<BigDecimal, BigDecimal> result3 = client1.calculateAdditionalVolumeDiscount(totalPrice3);

        BigDecimal percentage1 = BigDecimal.valueOf(5);
        BigDecimal percentage2 = BigDecimal.valueOf(7);
        BigDecimal percentage3 = BigDecimal.valueOf(7);

        BigDecimal discount1 = BigDecimal.valueOf(500.00).setScale(2, RoundingMode.HALF_UP);
        BigDecimal discount2 = BigDecimal.valueOf(2100.00).setScale(2, RoundingMode.HALF_UP);
        BigDecimal discount3 = BigDecimal.valueOf(2151.41);

        assertEquals(Pair.of(percentage1, discount1), result1);
        assertEquals(Pair.of(percentage2, discount2), result2);
        assertEquals(Pair.of(percentage3, discount3), result3);
    }

}
