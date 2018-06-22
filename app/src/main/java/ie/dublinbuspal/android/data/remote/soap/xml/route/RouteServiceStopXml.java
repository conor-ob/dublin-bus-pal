package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;

public class RouteServiceStopXml {

    @Element(name = "StopNumber", required = false)
    private String stopId;

    public RouteServiceStopXml() {
        super();
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

}
