package ie.dublinbuspal.domain.mapping.stop

import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.entity.StopEntity
import ie.dublinbuspal.domain.model.stop.Stop

class StopDomainMapper : Mapper<StopEntity, Stop> {

    override fun map(from: StopEntity): Stop {
        return Stop(from.id, from.name, Coordinate(from.latitude, from.longitude))
    }

}
