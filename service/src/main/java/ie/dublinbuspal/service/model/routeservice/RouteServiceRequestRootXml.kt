package ie.dublinbuspal.service.model.routeservice

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "GetStopDataByRoute", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
data class RouteServiceRequestRootXml(
        @field:Element(name = "route", required = false) val id: String
)
