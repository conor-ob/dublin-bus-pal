package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.RealTimeBusInformation
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationJson

class RealTimeBusInformationMapper : Mapper<RealTimeBusInformationJson, RealTimeBusInformation> {

    override fun map(from: RealTimeBusInformationJson): RealTimeBusInformation {
        return RealTimeBusInformation(from.route!!, from.destination!!, mapDueTime(from.duetime!!, from.expectedTime!!))
    }

    private fun mapDueTime(duetime: String, expectedTime: String): DueTime {
        return DueTime(duetime.toLong(), "") //TODO
    }

}
