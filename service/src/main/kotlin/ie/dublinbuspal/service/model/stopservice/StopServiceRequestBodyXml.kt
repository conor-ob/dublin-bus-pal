package ie.dublinbuspal.service.model.stopservice

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "soap12:Body", strict = false)
data class StopServiceRequestBodyXml(
        @field:Element(name = "GetRoutesServicedByStopNumber", required = false) val stopServiceRequestRootXml: StopServiceRequestRootXml
)
