package ie.dublinbuspal.android;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ie.dublinbuspal.android.data.local.DublinBusDatabase;
import ie.dublinbuspal.android.data.local.dao.BusStopDao;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DublinBusDatabaseTest {

    private DublinBusDatabase dublinBusDatabase;
    private BusStopDao busStopsDao;

    @Before
    public void createDatabase() {
        Context appContext = InstrumentationRegistry.getContext();
        dublinBusDatabase = Room.inMemoryDatabaseBuilder(appContext, DublinBusDatabase.class).build();
        busStopsDao = dublinBusDatabase.busStopDao();
    }

    @After
    public void closeDatabase() {
        dublinBusDatabase.close();
    }

    @Test
    public void testEmptyDatabase() {
//        List<BusStop> busStops = busStopsDao.selectAll();
//        assertThat(busStops.size(), is(0));
    }

}
