package ie.dublinbuspal.repository.livedata

import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.api.RtpiLiveData
import ie.dublinbuspal.util.Formatter
import ie.dublinbuspal.util.Operator
import ie.dublinbuspal.util.TimeUtils

object DublinBusLiveDataMapper : Mapper<RtpiLiveData, LiveData> {

    override fun map(from: RtpiLiveData): LiveData {
        return LiveData(
                routeId = from.routeId,
                operator = Operator.parse(from.operatorId),
                destination = mapDestination(from.destination),
                dueTime = mapDueTime(from.minutes, from.expectedTimestamp)
        )
    }

    private fun mapDueTime(minutes: Long, expectedTime: String): DueTime {
        val expectedTimeInstant = TimeUtils.dateTimeStampToInstant(expectedTime, Formatter.isoDateTime)
        return DueTime(minutes, TimeUtils.formatAsTime(expectedTimeInstant))
    }

    private fun mapDestination(destination: String): Destination {
        return Destination(getDestination(destination), getVia(destination))
    }

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
