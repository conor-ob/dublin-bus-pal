package ie.dublinbuspal.service.model.route

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Route", strict = false)
data class RouteXml(
        @field:Element(name = "Number") var id: String? = null,
        @field:Element(name = "From") var origin: String? = null,
        @field:Element(name = "Towards") var destination: String? = null
)
