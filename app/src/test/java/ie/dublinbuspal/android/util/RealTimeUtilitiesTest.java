package ie.dublinbuspal.android.util;

import static org.junit.Assert.assertEquals;

import ie.dublinbuspal.android.data.local.entity.RealTimeData;

import org.junit.Test;

public class RealTimeUtilitiesTest {

    @Test
    public void testDefaultDueTime() {
        RealTimeData realTimeData = new RealTimeData("41",
                "Swords Manor via Dublin Airport",
                "2017-10-06T22:17:38+01:00",
                "2017-10-06T21:56:00.983+01:00");
        String time = RealTimeUtilities.get24HourTime(realTimeData);
        assertEquals(time, "22:17");
        String dueTime = RealTimeUtilities.getDueTime(realTimeData);
        assertEquals(dueTime, "21 min");
    }

    @Test
    public void testImmediateDueTime() {
        RealTimeData realTimeData = new RealTimeData("41",
                "Swords Manor via Dublin Airport",
                "2017-10-06T21:56:58+01:00",
                "2017-10-06T21:56:00.214+01:00");
        String time = RealTimeUtilities.get24HourTime(realTimeData);
        assertEquals(time, "Due");
        String dueTime = RealTimeUtilities.getDueTime(realTimeData);
        assertEquals(dueTime, "Due");
    }

    @Test
    public void testUnexpectedDueTime() {
        RealTimeData realTimeData = new RealTimeData("41",
                "Swords Manor via Dublin Airport",
                "2017-10-06T21:53:38.852+01:00",
                "2017-10-06T21:56:00.983+01:00");
        String time = RealTimeUtilities.get24HourTime(realTimeData);
        assertEquals(time, "Late");
        String dueTime = RealTimeUtilities.getDueTime(realTimeData);
        assertEquals(dueTime, "Late");
    }

    @Test
    public void testDefaultRealTimeData() {
        RealTimeData realTimeData = new RealTimeData("41",
                "Swords Manor via Dublin Airport",
                "2017-10-06T21:59:38+01:00",
                "2017-10-06T21:56:00.983+01:00");
        String destination = RealTimeUtilities.getDestination(realTimeData);
        String via = RealTimeUtilities.getVia(realTimeData);
        assertEquals("Swords Manor", destination);
        assertEquals("via Dublin Airport", via);
    }

    @Test
    public void testUnexpectedRealTimeData() {
        RealTimeData realTimeData = new RealTimeData("9",
                "Platform 9 and Three Quarters",
                "2017-10-06T21:59:38+01:00",
                "2017-10-06T21:56:00.983+01:00");
        String destination = RealTimeUtilities.getDestination(realTimeData);
        String via = RealTimeUtilities.getVia(realTimeData);
        assertEquals("Platform 9 and Three Quarters", destination);
        assertEquals("", via);
    }

}
