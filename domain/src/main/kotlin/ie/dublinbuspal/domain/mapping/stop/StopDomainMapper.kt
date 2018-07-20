package ie.dublinbuspal.domain.mapping.stop

import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.entity.DetailedStopEntity
import ie.dublinbuspal.database.entity.StopEntity
import ie.dublinbuspal.domain.model.stop.Stop

class StopDomainMapper : Mapper<DetailedStopEntity, Stop> {

    override fun map(from: DetailedStopEntity): Stop {
        return Stop(from.id, from.customName()!!, Coordinate(from.latitude, from.longitude), from.customRoutes()!!)
    }

}
