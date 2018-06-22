package ie.dublinbuspal.android.data.remote.soap.xml.realtime;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "StopData", strict = false)
public class RealTimeDataXml {

    @Element(name = "MonitoredVehicleJourney_PublishedLineName", required = false)
    private String routeID;

    @Element(name = "MonitoredVehicleJourney_DestinationName", required = false)
    private String destination;

    @Element(name = "MonitoredCall_ExpectedArrivalTime", required = false)
    private String expectedTime;

    @Element(name = "Timestamp", required = false)
    private String timestamp;

    public RealTimeDataXml() {
        super();
    }

    public String getRouteID() {
        return routeID;
    }

    public String getDestination() {
        return destination;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
