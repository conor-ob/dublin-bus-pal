package ie.dublinbuspal.android.util;

import ie.dublinbuspal.android.data.remote.rss.xml.Item;
import ie.dublinbuspal.android.data.remote.rss.xml.Rss;

import java.util.Date;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RssUtilities {

    private static final String REGEX = "[0-9]{1,3}[a-zA-Z]?";
    private static final String ROUTE = "route";
    private static final String ROUTES = "routes";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private RssUtilities() {
        throw new UnsupportedOperationException();
    }

    public static SortedSet<String> getAffectedRoutes(Rss rss, Date date) {
        SortedSet<String> affectedRoutes = new TreeSet<>(AlphanumComparator.getInstance());
        Matcher matcher;
        for (Item item : rss.getChannel().getItems()) {
            if (shouldCheck(item, date)) {
                matcher = PATTERN.matcher(item.getTitle());
                while (matcher.find()) {
                    affectedRoutes.add(matcher.group().toUpperCase(Locale.UK));
                }
            }
        }
        return affectedRoutes;
    }

    private static boolean shouldCheck(Item item, Date now) {
        String title = item.getTitle();
        Date newsDate = DateUtilities.getDateFromRss(item.getPubDate());
        long difference = now.getTime() - newsDate.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(difference);
        return (title.toLowerCase().contains(ROUTE)
                || title.toLowerCase().contains(ROUTES))
                && days < 31;
    }

}
