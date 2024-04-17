package client;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class Client {
    private int id;
    private String name;
    private BigDecimal basicClientDiscount;
    private TreeMap<BigDecimal, BigDecimal> additionalVolumeDiscount;
    public static final int DECIMAL_PLACES = 2;
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    public static final BigDecimal NONE = BigDecimal.valueOf(-1);

    public Client(int id, String name, BigDecimal basicClientDiscount, TreeMap<BigDecimal, BigDecimal> additionalVolumeDiscount) {
        this.id = id;
        this.name = name;
        this.basicClientDiscount = basicClientDiscount;
        this.additionalVolumeDiscount = additionalVolumeDiscount;
    }

    public BigDecimal calculateBasicClientDiscount(BigDecimal totalPrice) {
        if (basicClientDiscount.compareTo(BigDecimal.valueOf(0)) == 0) return NONE;
        return basicClientDiscount.multiply(totalPrice).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    public Pair<BigDecimal, BigDecimal> calculateAdditionalVolumeDiscount(BigDecimal totalPrice) {
        //Binary search for the largest number lower than totalPrice
        BigDecimal costBound = additionalVolumeDiscount.lowerKey(totalPrice);

        if (costBound != null) {
            BigDecimal discount = additionalVolumeDiscount.get(costBound);
            if (discount.compareTo(BigDecimal.valueOf(0)) == 0) {
                return Pair.of(NONE, null);
            }
            return Pair.of(discount.multiply(HUNDRED).stripTrailingZeros(),
                discount.multiply(totalPrice).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP));
        } else {
            return Pair.of(NONE, null);
        }
    }

    public String getBasicClientDiscountReadableFormat() {
        return basicClientDiscount.multiply(HUNDRED).stripTrailingZeros().toString() + "%";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBasicClientDiscount(BigDecimal basicClientDiscount) {
        this.basicClientDiscount = basicClientDiscount;
    }

    public void setAdditionalVolumeDiscount(
        TreeMap<BigDecimal, BigDecimal> additionalVolumeDiscount) {
        this.additionalVolumeDiscount = additionalVolumeDiscount;
    }

    @Override
    public String toString() {
        return "client.Client{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", basicClientDiscount=" + basicClientDiscount +
            ", additionalVolumeDiscount=" + additionalVolumeDiscount +
            '}';
    }

}
