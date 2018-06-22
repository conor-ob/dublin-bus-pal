package ie.dublinbuspal.android.data.remote.soap.xml.stop;

import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteXml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class BusStopServiceResponse {

    @Path("soap:Body/GetRoutesServicedByStopNumberResponse/GetRoutesServicedByStopNumberResult")
    @ElementList(inline = true)
    private List<RouteXml> routeXmls;

    public BusStopServiceResponse() {
        super();
    }

    public List<RouteXml> getRouteXmls() {
        return routeXmls;
    }

    public void setRouteXmls(List<RouteXml> routeXmls) {
        this.routeXmls = routeXmls;
    }

}
