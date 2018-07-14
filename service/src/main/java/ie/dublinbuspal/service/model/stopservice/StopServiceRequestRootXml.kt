package ie.dublinbuspal.service.model.stopservice

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "GetRoutesServicedByStopNumber", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
data class StopServiceRequestRootXml(
        @field:Element(name = "stopId", required = false) val stopId: String
)
