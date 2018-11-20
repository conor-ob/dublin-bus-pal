package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.RealTimeBusInformation
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationJson

class RealTimeBusInformationMapper : Mapper<RealTimeBusInformationJson, RealTimeBusInformation> {

    override fun map(from: RealTimeBusInformationJson): RealTimeBusInformation {
        return RealTimeBusInformation(from.route!!, mapDestination(from.destination!!), mapDueTime(from.duetime!!, from.expectedTime!!))
    }

    private fun mapDestination(destination: String): Destination {
        return Destination(getDestination(destination), getVia(destination))
    }

    private fun mapDueTime(duetime: String, expectedTime: String): DueTime {
        if (duetime == "Due") {
            return DueTime(0L, "") //TODO
        }
        return DueTime(duetime.toLong(), "") //TODO
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
