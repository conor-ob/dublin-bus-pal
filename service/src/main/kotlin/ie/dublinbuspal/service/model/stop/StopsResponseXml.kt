package ie.dublinbuspal.service.model.stop

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(strict = false)
data class StopsResponseXml(
        @field:Path("soap:Body/GetAllDestinationsResponse/GetAllDestinationsResult/Destinations")
        @field:ElementList(inline = true) var stops: List<StopXml> = mutableListOf()
)
