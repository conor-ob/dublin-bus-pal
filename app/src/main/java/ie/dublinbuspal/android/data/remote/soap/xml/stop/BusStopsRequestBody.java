package ie.dublinbuspal.android.data.remote.soap.xml.stop;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body", strict = false)
public class BusStopsRequestBody {

    @Element(name = "GetAllDestinations",required = false)
    private BusStopsRequestRoot busStopsRequestRoot;

    public BusStopsRequestBody() {
        super();
    }

    public BusStopsRequestRoot getBusStopsRequestRoot() {
        return busStopsRequestRoot;
    }

    public void setBusStopsRequestRoot(BusStopsRequestRoot busStopsRequestRoot) {
        this.busStopsRequestRoot = busStopsRequestRoot;
    }

}
