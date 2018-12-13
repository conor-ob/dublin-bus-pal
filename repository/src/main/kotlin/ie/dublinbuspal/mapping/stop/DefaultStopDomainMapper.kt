package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.DefaultStopEntity
import ie.dublinbuspal.model.stop.DefaultStop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.util.Coordinate

class DefaultStopDomainMapper : Mapper<DefaultStopEntity, DefaultStop> {

    override fun map(from: DefaultStopEntity): DefaultStop {
        return DefaultStop(from.id, from.name, Coordinate(from.latitude, from.longitude))
    }

}
