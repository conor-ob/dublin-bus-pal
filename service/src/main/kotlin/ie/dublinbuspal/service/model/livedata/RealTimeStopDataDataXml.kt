package ie.dublinbuspal.service.model.livedata

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "StopData", strict = false)
data class RealTimeStopDataDataXml(
        @field:Element(name = "MonitoredVehicleJourney_PublishedLineName") var routeId: String? = null,
        @field:Element(name = "MonitoredVehicleJourney_DestinationName") var destination: String? = null,
        @field:Element(name = "Timestamp") var timestamp: String? = null,
        @field:Element(name = "MonitoredCall_ExpectedArrivalTime") var expectedTimestamp: String? = null
)