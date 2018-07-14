package ie.dublinbuspal.service.model.routeservice

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Route", strict = false)
data class RouteServiceXml(
        @field:Element(name = "RouteNumber") var id: String? = null,
        @field:Element(name = "RouteDescription") var description: String? = null,
        @field:Element(name = "StartStageName") var origin: String? = null,
        @field:Element(name = "EndStageName") var destination: String? = null
)
