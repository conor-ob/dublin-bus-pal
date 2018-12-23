package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.DublinBusGoAheadDublinLiveData
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationJson
import ie.dublinbuspal.util.TimeUtils

class DublinBusGoAheadDublinLiveDataEntityMapper : Mapper<RealTimeBusInformationJson, DublinBusGoAheadDublinLiveData> {

    override fun map(from: RealTimeBusInformationJson): DublinBusGoAheadDublinLiveData {
        return DublinBusGoAheadDublinLiveData(from.route!!, mapDestination(from.destination!!), mapDueTime(from.duetime!!, from.expectedTime!!))
    }

    private fun mapDestination(destination: String): Destination {
        return Destination(getDestination(destination), getVia(destination))
    }

    private fun mapDueTime(duetime: String, expectedTime: String): DueTime {
        val expectedTimeInstant = TimeUtils.toInstant(expectedTime)
        val time = TimeUtils.formateAsTime(expectedTimeInstant)
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
