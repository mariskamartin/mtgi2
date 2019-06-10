package cz.mariskamartin.mtgi2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private Utils() { /* library pattern */}

    public static long parseLong(String text, long defaultValue) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Date parseDateTime(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public static String date2timestamp(Date lastUpdated) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastUpdated);
    }
}
