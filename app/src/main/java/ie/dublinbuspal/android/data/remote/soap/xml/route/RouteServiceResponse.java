package ie.dublinbuspal.android.data.remote.soap.xml.route;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class RouteServiceResponse {

    @Path("soap:Body/GetStopDataByRouteResponse/GetStopDataByRouteResult/diffgr:diffgram/StopDataByRoute")
    @Element(name = "Route")
    private RouteServiceXml routeServiceXml;

    @Path("soap:Body/GetStopDataByRouteResponse/GetStopDataByRouteResult/diffgr:diffgram/StopDataByRoute")
    @ElementList(inline = true, required = false)
    private List<RouteServiceInboundStopXml> inboundStopXmls;

    @Path("soap:Body/GetStopDataByRouteResponse/GetStopDataByRouteResult/diffgr:diffgram/StopDataByRoute")
    @ElementList(inline = true, required = false)
    private List<RouteServiceOutboundStopXml> outboundStopXmls;

    public RouteServiceResponse() {
        super();
    }

    public RouteServiceXml getRouteServiceXml() {
        return routeServiceXml;
    }

    public List<RouteServiceInboundStopXml> getInboundStopXmls() {
        return inboundStopXmls;
    }

    public List<RouteServiceOutboundStopXml> getOutboundStopXmls() {
        return outboundStopXmls;
    }

    public void setRouteServiceXml(RouteServiceXml routeServiceXml) {
        this.routeServiceXml = routeServiceXml;
    }

    public void setInboundStopXmls(List<RouteServiceInboundStopXml> inboundStopXmls) {
        this.inboundStopXmls = inboundStopXmls;
    }

    public void setOutboundStopXmls(List<RouteServiceOutboundStopXml> outboundStopXmls) {
        this.outboundStopXmls = outboundStopXmls;
    }

}
