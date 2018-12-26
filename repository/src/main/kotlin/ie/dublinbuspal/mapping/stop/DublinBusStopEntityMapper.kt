package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.DublinBusStopEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson

class DublinBusStopEntityMapper : Mapper<StopJson, DublinBusStopEntity> {

    override fun map(from: StopJson): DublinBusStopEntity {
        return DublinBusStopEntity(from.displayId!!, from.fullName!!, from.latitude!!.toDouble(), from.longitude!!.toDouble(), from.operators!![0].routes!! )
    }

}
