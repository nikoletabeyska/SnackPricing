package product.markup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkupFactory {
    public static Pattern percentRegex = Pattern.compile("\\d+%");
    public static Pattern currencyRegex = Pattern.compile("\\d+(\\.\\d+)? EUR/unit");

    public static Markup createMarkupFactory(String userInput) {
        Matcher percentMatcher = percentRegex.matcher(userInput);
        Matcher currencyMatcher = currencyRegex.matcher(userInput);

        if (percentMatcher.matches()) {
            String numericPart = userInput.split(" ")[0].replace("%", "");
            return new PercentageMarkup(new BigDecimal(numericPart).setScale(2, RoundingMode.HALF_UP));
        } else if (currencyMatcher.matches()) {
            String numericPart = userInput.split(" ")[0];
            return new EURMarkup(new BigDecimal(numericPart).setScale(2, RoundingMode.HALF_UP));
        } else {
            throw new IllegalArgumentException("Invalid product type: " + userInput + "\n");
        }
    }
}
