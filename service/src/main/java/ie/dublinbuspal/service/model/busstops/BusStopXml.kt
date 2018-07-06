package ie.dublinbuspal.service.model.busstops

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Destination", strict = false)
data class BusStopXml(
        @field:Element(name = "StopNumber") var id: String? = null,
        @field:Element(name = "Description") var name: String? = null,
        @field:Element(name = "Latitude") var latitude: String? = null,
        @field:Element(name = "Longitude") var longitude: String? = null,
        @field:Element(name = "Routes", required = false) var routes: String? = null,
        @field:Element(name = "Type", required = false) var type: String? = null
)
