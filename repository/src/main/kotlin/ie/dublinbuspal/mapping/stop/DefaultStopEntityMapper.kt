package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.DefaultStopEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopXml

class DefaultStopEntityMapper : Mapper<StopXml, DefaultStopEntity> {

    override fun map(from: StopXml): DefaultStopEntity {
        return DefaultStopEntity(from.id!!, from.name!!, from.latitude!!.toDouble(), from.longitude!!.toDouble())
    }

}
