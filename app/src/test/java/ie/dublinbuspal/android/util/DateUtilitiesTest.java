package ie.dublinbuspal.android.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtilitiesTest {

    @Test
    public void testDefaultDifferenceInMinutes() {
        String current = "2017-10-06T21:56:42.997+01:00";
        String future = "2017-10-06T22:14:41.321+01:00";
        long differenceInMinutes = DateUtilities.getDifferenceInMinutes(current, future);
        assertEquals(differenceInMinutes, 17L);
    }

    @Test
    public void testUnexpectedDifferenceInMinutes() {
        String current = "2017-10-06T21:56:00.997+01:00";
        String future = "2017-10-06T21:52:00.328+01:00";
        long differenceInMinutes = DateUtilities.getDifferenceInMinutes(current, future);
        assertEquals(differenceInMinutes, -4L);
    }

    @Test
    public void testParseRssDate() {
        String dateString = "Wed, 27 Dec 2017 16:39:12 GMT";
        Date date = DateUtilities.getDateFromRss(dateString);
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        assertEquals(4, dayOfWeek);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        assertEquals(27, dayOfMonth);
        int month = calendar.get(Calendar.MONTH);
        assertEquals(11, month);
        int year = calendar.get(Calendar.YEAR);
        assertEquals(2017, year);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        assertEquals(16, hourOfDay);
        int minute = calendar.get(Calendar.MINUTE);
        assertEquals(39, minute);
        int second = calendar.get(Calendar.SECOND);
        assertEquals(12, second);
    }

}
