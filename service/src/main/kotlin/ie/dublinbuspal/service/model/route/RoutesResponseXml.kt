package ie.dublinbuspal.service.model.route

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(strict = false)
data class RoutesResponseXml(
        @field:Path("soap:Body/GetRoutesResponse/GetRoutesResult/Routes")
        @field:ElementList(inline = true) var routes: List<RouteXml> = mutableListOf()
)
