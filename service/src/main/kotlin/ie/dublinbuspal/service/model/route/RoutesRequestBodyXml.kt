package ie.dublinbuspal.service.model.route

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "soap12:Body", strict = false)
data class RoutesRequestBodyXml(
        @field:Element(name = "GetRoutes", required = false) val routesRequestRootXml: RoutesRequestRootXml
)
