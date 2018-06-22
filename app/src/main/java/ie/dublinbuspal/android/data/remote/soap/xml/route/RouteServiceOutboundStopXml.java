package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "OutboundStop", strict = false)
public class RouteServiceOutboundStopXml {

    @Element(name = "StopNumber", required = false)
    private String stopId;

    public RouteServiceOutboundStopXml() {
        super();
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

}
