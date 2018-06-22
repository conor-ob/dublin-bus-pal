package ie.dublinbuspal.android.util;

import ie.dublinbuspal.android.data.remote.rss.xml.Item;

import java.util.Comparator;
import java.util.Date;

public class RssDateComparator implements Comparator<Item> {

    private static RssDateComparator INSTANCE;

    private RssDateComparator() {
        //nothing
    }

    public static RssDateComparator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RssDateComparator();
        }
        return INSTANCE;
    }

    @Override
    public int compare(Item item1, Item item2) {
        String pubDate1 = item1.getPubDate();
        String pubDate2 = item2.getPubDate();
        Date date1 = DateUtilities.getDateFromRss(pubDate1);
        Date date2 = DateUtilities.getDateFromRss(pubDate2);
        return -date1.compareTo(date2);
    }

}
