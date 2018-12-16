package ie.dublinbuspal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class DateUtilities {

    private static final SimpleDateFormat DATE_FORMAT_ISO_8601 =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.UK);
    private static final SimpleDateFormat DATE_FORMAT_24_HOUR =
            new SimpleDateFormat("HH:mm", Locale.UK);
    private static final SimpleDateFormat DATE_FORMAT_RSS =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.UK);
    private static final SimpleDateFormat DATE_FORMAT_RSS_OLD_ITEM =
            new SimpleDateFormat("dd MMM yyyy", Locale.UK);
    private static final SimpleDateFormat DATE_FORMAT_RSS_DEFAULT =
            new SimpleDateFormat("d MMM", Locale.UK);
    private static final SimpleDateFormat DATE_FORMAT_LAST_UPDATED =
            new SimpleDateFormat("d MMM, HH:mm", Locale.UK);

    private DateUtilities() {
        throw new UnsupportedOperationException();
    }

    static long getDifferenceInMinutes(String current, String future) {
        try {
            long currentTime = DATE_FORMAT_ISO_8601.parse(current).getTime();
            long futureTime = DATE_FORMAT_ISO_8601.parse(future).getTime();
            long difference = futureTime - currentTime;
            return TimeUnit.MILLISECONDS.toMinutes(difference);
        } catch (ParseException e) {
            // nada
        }
        return 0L;
    }

    static String get24HourTime(String expectedTimestamp) {
        try {
            Date date = DATE_FORMAT_ISO_8601.parse(expectedTimestamp);
            return DATE_FORMAT_24_HOUR.format(date);
        } catch (ParseException e) {
            // nada
        }
        return null;
    }

    static Date getDateFromRss(String timestamp) {
        try {
            return DATE_FORMAT_RSS.parse(timestamp);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getRssItemAge(String timestamp) {
        long now = new Date().getTime();
        long itemTime = getDateFromRss(timestamp).getTime();
        long difference = now - itemTime;
        long days = TimeUnit.MILLISECONDS.toDays(difference);
        if (days > 365) {
            Date itemDate = new Date(itemTime);
            return DATE_FORMAT_RSS_OLD_ITEM.format(itemDate);
        }
        long hours = TimeUnit.MILLISECONDS.toHours(difference);
        if (hours > 23) {
            Date itemDate = new Date(itemTime);
            return DATE_FORMAT_RSS_DEFAULT.format(itemDate);
        }
        if (hours >= 1) {
            return String.format(Locale.UK, "%sh", hours);
        }
        return "Just now";
    }

    public static String formatLastUpdatedTime(Date date) {
        return DATE_FORMAT_LAST_UPDATED.format(date);
    }

}
