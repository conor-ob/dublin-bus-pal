package ie.dublinbuspal.service.model.route

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "GetRoutes", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
data class RoutesRequestRootXml(
        @field:Element(name = "filter", required = false) val filter: String
)
