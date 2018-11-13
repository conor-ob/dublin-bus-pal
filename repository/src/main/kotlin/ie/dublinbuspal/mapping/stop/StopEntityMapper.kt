package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.StopEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopXml

class StopEntityMapper : Mapper<StopXml, StopEntity> {

    override fun map(from: StopXml): StopEntity {
        return StopEntity(from.id!!, from.name!!, from.latitude!!.toDouble(), from.longitude!!.toDouble())
    }

}
