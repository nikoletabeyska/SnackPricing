package utils;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class InfoParserTest {

    @Test
    void testParsePercentage() {
        assertEquals(new BigDecimal("0.25"), InfoParser.parsePercentage("25%"));
        assertEquals(new BigDecimal("1.00"), InfoParser.parsePercentage("100%"));

    }

    @Test
    void testParseThreshold() {
        assertEquals(new BigDecimal("10000.00"), InfoParser.parseThreshold("Above EUR 10,000"));
        assertEquals(new BigDecimal("30222.00"), InfoParser.parseThreshold("Above EUR 30,222"));

    }

    @Test
    public void testParseVolumeDiscount() {

        JSONObject volumeDiscount = new JSONObject();
        volumeDiscount.put("Above EUR 1000", "10%");
        volumeDiscount.put("Above EUR 2000", "15%");

        TreeMap<BigDecimal, BigDecimal> parsedVolumeDiscount = InfoParser.parseVolumeDiscount(volumeDiscount);

        assertEquals(new BigDecimal("1000.00"), parsedVolumeDiscount.firstKey());
        assertEquals(new BigDecimal("0.10"), parsedVolumeDiscount.firstEntry().getValue());
        assertEquals(new BigDecimal("2000.00"), parsedVolumeDiscount.higherKey(new BigDecimal("1000")));
        assertEquals(new BigDecimal("0.15"), parsedVolumeDiscount.higherEntry(new BigDecimal("1000")).getValue());
    }
}