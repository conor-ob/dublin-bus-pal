package ie.dublinbuspal.service.model.livedata

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "GetRealTimeStopData", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
data class LiveDataRequestRootXml(
        @field:Element(name = "stopId", required = false) val stopId: String,
        @field:Element(name = "forceRefresh", required = false) val forceRefresh: String
)
