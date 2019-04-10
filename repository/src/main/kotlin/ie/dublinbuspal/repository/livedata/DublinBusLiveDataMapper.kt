package ie.dublinbuspal.repository.livedata

import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.api.rtpi.RtpiRealTimeBusInformationJson
import ie.dublinbuspal.util.Formatter
import ie.dublinbuspal.util.Operator
import ie.dublinbuspal.util.TimeUtils

object DublinBusLiveDataMapper : Mapper<RtpiRealTimeBusInformationJson, LiveData> {

    override fun map(from: RtpiRealTimeBusInformationJson): LiveData {
        return LiveData(
                routeId = from.route!!,
                operator = Operator.parse(from.operator!!),
                destination = mapDestination(from.destination!!),
                dueTime = mapDueTime(from.arrivalDateTime!!)
        )
    }

    private fun mapDueTime(expectedTime: String): DueTime {
        val timestampInstant = TimeUtils.now()
        val expectedTimeInstant = TimeUtils.dateTimeStampToInstant(expectedTime, Formatter.isoDateTime)
        val minutes = TimeUtils.minutesBetween(timestampInstant, expectedTimeInstant)
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
