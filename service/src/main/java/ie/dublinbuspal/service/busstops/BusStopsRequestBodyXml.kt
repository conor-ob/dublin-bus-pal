package ie.dublinbuspal.service.busstops

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "soap12:Body", strict = false)
data class BusStopsRequestBodyXml(
        @field:Element(name = "GetAllDestinations", required = false) val busStopsRequestRootXml: BusStopsRequestRootXml
)
