package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body", strict = false)
public class RoutesRequestBody {

    @Element(name = "GetRoutes",required = false)
    private RoutesRequestRoot routesRequestRoot;

    public RoutesRequestRoot getRoutesRequestRoot() {
        return routesRequestRoot;
    }

    public void setRoutesRequestRoot(RoutesRequestRoot routesRequestRoot) {
        this.routesRequestRoot = routesRequestRoot;
    }

}
