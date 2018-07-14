package ie.dublinbuspal.service.model.routeservice

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(strict = false)
data class RouteServiceResponseXml(
        @field:Path("soap:Body/GetStopDataByRouteResponse/GetStopDataByRouteResult/diffgr:diffgram/StopDataByRoute")
        @field:Element(name = "Route") var routeService: RouteServiceXml? = null,
        @field:Path("soap:Body/GetStopDataByRouteResponse/GetStopDataByRouteResult/diffgr:diffgram/StopDataByRoute")
        @field:ElementList(inline = true, required = false) var inboundStopXmls: List<RouteServiceInboundStopXml> = mutableListOf(),
        @field:Path("soap:Body/GetStopDataByRouteResponse/GetStopDataByRouteResult/diffgr:diffgram/StopDataByRoute")
        @field:ElementList(inline = true, required = false) var outboundStopXmls: List<RouteServiceOutboundStopXml> = mutableListOf()
)
