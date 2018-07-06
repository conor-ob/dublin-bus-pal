package ie.dublinbuspal.service.busstops

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(strict = false)
data class BusStopsResponseXml(
        @field:Path("soap:Body/GetAllDestinationsResponse/GetAllDestinationsResult/Destinations")
        @field:ElementList(inline = true) val busStops: List<BusStopXml>
)
