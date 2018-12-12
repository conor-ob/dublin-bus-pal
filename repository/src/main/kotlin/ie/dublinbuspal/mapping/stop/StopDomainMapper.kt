package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.StopEntity
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.util.Coordinate

class StopDomainMapper : Mapper<StopEntity, Stop> {

    override fun map(from: StopEntity): Stop {
        return Stop(from.id, from.name, Coordinate(from.latitude, from.longitude))
    }

}
