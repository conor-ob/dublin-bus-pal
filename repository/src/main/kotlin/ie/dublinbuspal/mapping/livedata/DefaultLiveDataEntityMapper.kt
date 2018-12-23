package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.livedata.RealTimeStopDataDataXml
import ie.dublinbuspal.util.TimeUtils

class DefaultLiveDataEntityMapper : Mapper<RealTimeStopDataDataXml, RealTimeStopData> {

    override fun map(from: RealTimeStopDataDataXml): RealTimeStopData {
        return RealTimeStopData(from.routeId!!, mapDestination(from.destination!!), mapDueTime(from.timestamp!!, from.expectedTimestamp!!))
    }

    private fun mapDestination(destination: String): Destination {
        return Destination(getDestination(destination), getVia(destination))
    }

    private fun mapDueTime(timestamp: String, expectedTime: String): DueTime {
        val timestampInstant = TimeUtils.toInstant(timestamp)
        val expectedTimeInstant = TimeUtils.toInstant(expectedTime)
        val minutes = TimeUtils.minutesBetween(timestampInstant, expectedTimeInstant)
        return DueTime(minutes, TimeUtils.formateAsTime(expectedTimeInstant))
    }

    //TODO duplicate code
    private fun getDestination(destination: String): String {
        if (destination.contains("via")) {
            val i = destination.indexOf("via")
            return destination.substring(0, i).trim { it <= ' ' }
        }
        return destination
    }

    private fun getVia(destination: String): String? {
        if (destination.contains("via")) {
            val i = destination.indexOf("via")
            return destination.substring(i, destination.length).trim { it <= ' ' }
        }
        return null
    }

}
