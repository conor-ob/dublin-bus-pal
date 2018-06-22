package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Route", strict = false)
public class RouteServiceXml {

    @Element(name = "RouteNumber", required = false)
    private String routeId;

    @Element(name = "RouteDescription", required = false)
    private String routeDescription;

    @Element(name = "StartStageName", required = false)
    private String routeOrigin;

    @Element(name = "EndStageName", required = false)
    private String routeDestination;

    public RouteServiceXml() {
        super();
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public String getRouteOrigin() {
        return routeOrigin;
    }

    public String getRouteDestination() {
        return routeDestination;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public void setRouteOrigin(String routeOrigin) {
        this.routeOrigin = routeOrigin;
    }

    public void setRouteDestination(String routeDestination) {
        this.routeDestination = routeDestination;
    }

}
