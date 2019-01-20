package cz.mariskamartin.mtgi2;

public class Utils {
    private Utils() { /* library pattern */}

    public static long parseLong(String text, long defaultValue) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
