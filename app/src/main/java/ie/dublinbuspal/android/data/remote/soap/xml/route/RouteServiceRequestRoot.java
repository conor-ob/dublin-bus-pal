package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "GetStopDataByRoute", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
public class RouteServiceRequestRoot {

    @Element(name = "route", required = false)
    private String route;

    public RouteServiceRequestRoot() {
        super();
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
