// Made by Equips friend

package studio.ecoprojects.ecozero.economy;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BalanceFormatter {

    private static final Pattern PATTERN = Pattern.compile("^(\\d+\\.?\\d*)(\\D+)");

    private final List<String> suffixes;

    public BalanceFormatter() {
        this.suffixes = Arrays.asList("", "K", "M", "B", "T", "Q", "QN");
    }

    public BalanceFormatter(List<String> suffixes) {
        this.suffixes = suffixes;
    }

    public String formatNumber(Double value) {
        int index = 0;

        double tmp;
        while ((tmp = value / 1000) >= 1) {
            value = tmp;
            ++index;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(value) + this.suffixes.get(index);
    }

    public double parseString(String value) throws Exception {
        try {
            return Double.parseDouble(value);
        } catch (Exception ignored) {}

        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.find()) {
            throw new Exception("Invalid format");
        }

        double amount = Double.parseDouble(matcher.group(1));
        String suffix = matcher.group(2);

        int index = this.suffixes.indexOf(suffix.toUpperCase());

        return amount * Math.pow(1000, index);
    }

}
