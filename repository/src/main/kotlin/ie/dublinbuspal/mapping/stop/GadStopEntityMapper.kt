package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.GadStopEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson

class GadStopEntityMapper : Mapper<StopJson, GadStopEntity> {

    override fun map(from: StopJson): GadStopEntity {
        return GadStopEntity(from.displayId!!, from.fullName!!, from.latitude!!.toDouble(), from.longitude!!.toDouble(), from.operators!![0].routes!! )
    }

}
