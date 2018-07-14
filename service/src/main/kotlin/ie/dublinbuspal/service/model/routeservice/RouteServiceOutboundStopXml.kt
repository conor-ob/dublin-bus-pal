package ie.dublinbuspal.service.model.routeservice

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "OutboundStop", strict = false)
data class RouteServiceOutboundStopXml(
        @field:Element(name = "StopNumber", required = false) var id: String? = null
)
