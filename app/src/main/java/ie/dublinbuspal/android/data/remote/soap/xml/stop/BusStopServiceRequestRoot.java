package ie.dublinbuspal.android.data.remote.soap.xml.stop;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "GetRoutesServicedByStopNumber", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
public class BusStopServiceRequestRoot {

    @Element(name = "stopId", required = false)
    private String stopId;

    public BusStopServiceRequestRoot() {
        super();
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

}
