package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.livedata.RealTimeBusInformation
import ie.dublinbuspal.repository.Mapper

class RealTimeBusInformationLiveDataMapper : Mapper<RealTimeBusInformation, LiveData> {

    override fun map(from: RealTimeBusInformation): LiveData {
        return LiveData(from.routeId, from.destination, from.dueTime)
    }

}
