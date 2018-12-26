package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.livedata.DublinBusGoAheadDublinLiveData
import ie.dublinbuspal.repository.Mapper

class DublinBusGoAheadDublinLiveDataDomainMapper : Mapper<DublinBusGoAheadDublinLiveData, LiveData> {

    override fun map(from: DublinBusGoAheadDublinLiveData): LiveData {
        return LiveData(from.routeId, from.destination, from.dueTime)
    }

}
