package ie.dublinbuspal.android.util;

import static org.junit.Assert.assertEquals;

import ie.dublinbuspal.android.data.remote.rss.xml.Item;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RssDateComparatorTest {

    @Test
    public void testReverseChronologicalSorting() {
        Item item1 = new Item();
        item1.setPubDate("Fri, 27 Oct 2017 16:39:12 GMT");
        Item item2 = new Item();
        item2.setPubDate("Fri, 03 Nov 2017 13:44:35 GMT");
        Item item3 = new Item();
        item3.setPubDate("Fri, 29 Sep 2017 12:46:32 GMT");
        Item item4 = new Item();
        item4.setPubDate("Mon, 04 Jul 2016 11:53:12 GMT");
        Item item5 = new Item();
        item5.setPubDate("Fri, 27 Oct 2017 09:52:04 GMT");
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        Collections.sort(items, RssDateComparator.getInstance());
        assertEquals(item2, items.get(0));
        assertEquals(item1, items.get(1));
        assertEquals(item5, items.get(2));
        assertEquals(item3, items.get(3));
        assertEquals(item4, items.get(4));
    }

}
