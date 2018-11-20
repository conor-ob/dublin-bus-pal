package ie.dublinbuspal.service.model.livedata

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(strict = false)
data class LiveDataResponseXml(
        @field:Path("soap:Body/GetRealTimeStopDataResponse/GetRealTimeStopDataResult/diffgr:diffgram/DocumentElement")
        @field:ElementList(inline = true) var realTimeStopData: List<RealTimeStopDataDataXml> = mutableListOf()
)
