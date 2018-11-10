package ie.dublinbuspal.service.model.livedata

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "soap12:Body", strict = false)
data class LiveDataRequestBodyXml(
        @field:Element(name = "GetRealTimeStopData", required = false) val liveDataRequestRootXml: LiveDataRequestRootXml
)
