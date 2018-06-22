package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "InboundStop", strict = false)
public class RouteServiceInboundStopXml {

    @Element(name = "StopNumber", required = false)
    private String stopId;

    public RouteServiceInboundStopXml() {
        super();
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

}
