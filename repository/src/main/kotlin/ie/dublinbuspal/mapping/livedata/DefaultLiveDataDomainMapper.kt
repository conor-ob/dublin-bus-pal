package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.repository.Mapper

class DefaultLiveDataDomainMapper : Mapper<RealTimeStopData, LiveData> {

    override fun map(from: RealTimeStopData): LiveData {
        return LiveData(from.routeId, from.destination, from.dueTime)
    }

}
