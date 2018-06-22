package ie.dublinbuspal.android.data.remote.soap.xml.stop;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class BusStopsResponse {

    @Path("soap:Body/GetAllDestinationsResponse/GetAllDestinationsResult/Destinations")
    @ElementList(inline = true)
    private List<BusStopXml> busStops;

    public BusStopsResponse() {
        super();
    }

    public List<BusStopXml> getBusStops() {
        return busStops;
    }

    public void setBusStops(List<BusStopXml> busStops) {
        this.busStops = busStops;
    }

}
