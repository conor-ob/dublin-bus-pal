package ie.dublinbuspal.android.data.remote.soap.xml.stop;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body", strict = false)
public class BusStopServiceRequestBody {

    @Element(name = "GetRoutesServicedByStopNumber",required = false)
    private BusStopServiceRequestRoot busStopServiceRequestRoot;

    public BusStopServiceRequestBody() {
        super();
    }

    public BusStopServiceRequestRoot getBusStopServiceRequestRoot() {
        return busStopServiceRequestRoot;
    }

    public void setBusStopServiceRequestRoot(BusStopServiceRequestRoot busStopServiceRequestRoot) {
        this.busStopServiceRequestRoot = busStopServiceRequestRoot;
    }

}
