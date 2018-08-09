package ie.dublinbuspal.domain.mapping.stop

import ie.dublinbuspal.domain.repository.Mapper
import ie.dublinbuspal.database.entity.StopEntity
import ie.dublinbuspal.service.model.stop.StopXml

class StopEntityMapper : Mapper<StopXml, StopEntity> {

    override fun map(from: StopXml): StopEntity {
        return StopEntity(from.id!!, from.name!!, from.latitude!!.toDouble(), from.longitude!!.toDouble())
    }

}
