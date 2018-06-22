package ie.dublinbuspal.android.data;

import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import ie.dublinbuspal.android.MockRemoteDataSource;
import ie.dublinbuspal.android.MockRestDataSource;
import ie.dublinbuspal.android.MockRssDataSource;
import ie.dublinbuspal.android.util.InternetManager;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DublinBusRepositoryTest {

    private DublinBusRepository repository;

    @Before
    public void setup() {
        repository = new DublinBusRepositoryImpl(null, null,
                new MockRemoteDataSource(), new MockRestDataSource(), new MockRssDataSource(null),
                null, new InternetManager(null));
    }

    @Test
    public void testCondensedRealTimeData() throws Exception {
        List<RealTimeData> realTimeData = repository.getRealTimeData("1");
        Map<String, List<RealTimeData>> condensedRealTimeData =
                repository.getCondensedRealTimeData("1");
        assertEquals(countUniqueRoutes(realTimeData), condensedRealTimeData.size());
        assertEquals(countFlattenedMap(condensedRealTimeData), realTimeData.size());
    }

    private int countFlattenedMap(Map<String, List<RealTimeData>> condensedRealTimeData) {
        int total = 0;
        for (List<RealTimeData> list : condensedRealTimeData.values()) {
            total += list.size();
        }
        return total;
    }

    private int countUniqueRoutes(List<RealTimeData> realTimeData) {
        Set<String> routes = new HashSet<>();
        for (RealTimeData data : realTimeData) {
            routes.add(data.getRoute());
        }
        return routes.size();
    }

}
