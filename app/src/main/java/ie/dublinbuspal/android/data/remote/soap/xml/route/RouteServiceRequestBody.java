package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body", strict = false)
public class RouteServiceRequestBody {

    @Element(name = "GetStopDataByRoute",required = false)
    private RouteServiceRequestRoot routeServiceRequestRoot;

    public RouteServiceRequestBody() {
        super();
    }

    public RouteServiceRequestRoot getRouteServiceRequestRoot() {
        return routeServiceRequestRoot;
    }

    public void setRouteServiceRequestRoot(RouteServiceRequestRoot routeServiceRequestRoot) {
        this.routeServiceRequestRoot = routeServiceRequestRoot;
    }

}
