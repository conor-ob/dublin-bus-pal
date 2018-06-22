package ie.dublinbuspal.android.util;

import ie.dublinbuspal.android.data.remote.rss.xml.Rss;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class RssUtilitiesTest {

    @Test
    public void testGetAffectedRoutes() throws Exception {
//        SortedSet<String> expected = new TreeSet<>(AlphanumComparator.getInstance());
//        expected.add("7");
//        expected.add("40D");
//        expected.add("84");
//        expected.add("111");
//        expected.add("238");
//        expected.add("747");
//
//        ClassLoader classLoader = getClass().getClassLoader();
//        URL resource = classLoader.getResource("rss.xml");
//        File file = new File(resource.getPath());
//        Serializer ser = new Persister();
//        Rss rss = ser.read(Rss.class, file);
//
//        assertEquals(expected, RssUtilities.getAffectedRoutes(rss,
//                new Date(2017-1900, 10, 14)));
    }

}
