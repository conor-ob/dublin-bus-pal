package ie.dublinbuspal.service.model.stop

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "soap12:Body", strict = false)
data class StopsRequestBodyXml(
        @field:Element(name = "GetAllDestinations", required = false) val stopsRequestRootXml: StopsRequestRootXml
)
