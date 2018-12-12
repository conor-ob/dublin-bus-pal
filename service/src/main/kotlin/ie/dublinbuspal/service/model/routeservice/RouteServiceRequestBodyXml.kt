package ie.dublinbuspal.service.model.routeservice

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "soap12:Body", strict = false)
data class RouteServiceRequestBodyXml(
        @field:Element(name = "GetStopDataByRoute", required = false) val routeServiceRequestRootXml: RouteServiceRequestRootXml
)
