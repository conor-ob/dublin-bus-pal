package ie.dublinbuspal.service.model.stopservice

import ie.dublinbuspal.service.model.route.RouteXml
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(strict = false)
data class StopServiceResponseXml(
        @field:Path("soap:Body/GetRoutesServicedByStopNumberResponse/GetRoutesServicedByStopNumberResult")
        @field:ElementList(inline = true, required = false) var routes: List<RouteXml> = mutableListOf()
)
