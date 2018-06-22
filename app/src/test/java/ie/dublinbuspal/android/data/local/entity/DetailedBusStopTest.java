package ie.dublinbuspal.android.data.local.entity;

import static java.util.Arrays.asList;

public class DetailedBusStopTest extends AbstractEntityTest<DetailedBusStop> {

    @Override
    protected DetailedBusStop newInstance1() {
        return new DetailedBusStop("1", "Pearse Street",
                53.2125, -6.541, asList("7D", "46A", "4", "145"),
                asList("118", "142"), asList("46A", "39"), "Howiye");
    }

    @Override
    protected DetailedBusStop newInstance2() {
        return new DetailedBusStop("2", "Northumberland Road",
                53.9231, -6.0214, asList("18", "7A", "84X"),
                asList("117", "184", "15"), asList("145", "142"),
                "Sure isn't only gorgeous out?");
    }

}
