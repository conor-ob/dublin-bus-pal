package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Route", strict = false)
public class RouteXml {

    @Element(name = "Number", required = false)
    private String routeID;

    @Element(name = "From", required = false)
    private String routeStart;

    @Element(name = "Towards", required = false)
    private String routeEnd;

    public RouteXml() {
        super();
    }

    public String getRouteID() {
        return routeID;
    }

    public String getRouteStart() {
        return routeStart;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

}
