package ie.dublinbuspal.android;

import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;
import ie.dublinbuspal.android.data.remote.rest.RestDataSource;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MockRestDataSource implements RestDataSource {

    private List<UncheckedBusStopService> uncheckedBusStopServices;

    public MockRestDataSource() {
        super();
    }

    @Override
    public List<UncheckedBusStopService> getUncheckedBusStopServices() throws Exception {
        if (uncheckedBusStopServices == null) {
            uncheckedBusStopServices = new ArrayList<>();
            uncheckedBusStopServices.add(new UncheckedBusStopService("1",
                    asList("46A","145")));
            uncheckedBusStopServices.add(new UncheckedBusStopService("2",
                    asList("46A", "145")));
            uncheckedBusStopServices.add(new UncheckedBusStopService("3",
                    asList("46A", "145")));
            uncheckedBusStopServices.add(new UncheckedBusStopService("4",
                    asList("46A", "145")));
            uncheckedBusStopServices.add(new UncheckedBusStopService("5",
                    asList("46A", "145")));
            uncheckedBusStopServices.add(new UncheckedBusStopService("6",
                    asList("46A", "145")));
        }
        return uncheckedBusStopServices;
    }

}
