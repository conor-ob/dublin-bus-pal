package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.DublinBusGoAheadDublinLiveData
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationJson
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit

class DublinBusGoAheadDublinLiveDataEntityMapper : Mapper<RealTimeBusInformationJson, DublinBusGoAheadDublinLiveData> {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    override fun map(from: RealTimeBusInformationJson): DublinBusGoAheadDublinLiveData {
        return DublinBusGoAheadDublinLiveData(from.route!!, mapDestination(from.destination!!), mapDueTime(from.duetime!!, from.expectedTime!!))
    }

    private fun mapDestination(destination: String): Destination {
        return Destination(getDestination(destination), getVia(destination))
    }

    private fun mapDueTime(duetime: String, expectedTime: String): DueTime {
        val time = LocalDateTime.parse(expectedTime, formatter).toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString()
        if (duetime == "Due") {
            return DueTime(0L, time)
        }
        return DueTime(duetime.toLong(), time)
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
