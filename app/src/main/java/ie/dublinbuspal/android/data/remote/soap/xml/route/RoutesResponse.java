package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class RoutesResponse {

    @Path("soap:Body/GetRoutesResponse/GetRoutesResult/Routes")
    @ElementList(inline = true)
    private List<RouteXml> routes;

    public RoutesResponse() {
        super();
    }

    public List<RouteXml> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteXml> routes) {
        this.routes = routes;
    }

}
