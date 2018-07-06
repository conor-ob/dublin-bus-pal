package ie.dublinbuspal.service.busstops

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Destination", strict = false)
data class BusStopXml(
        @field:Element(name = "StopNumber") val id: String,
        @field:Element(name = "Description") val name: String,
        @field:Element(name = "Latitude") val latitude: String,
        @field:Element(name = "Longitude") val longitude: String,
        @field:Element(name = "Routes", required = false) var routes: String? = null,
        @field:Element(name = "Type", required = false) var type: String? = null
)
