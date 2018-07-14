package ie.dublinbuspal.service.model.routeservice

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "InboundStop", strict = false)
data class RouteServiceInboundStopXml(
        @field:Element(name = "StopNumber", required = false) var id: String? = null
)
