package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.Coordinate
import ie.dublinbuspal.data.entity.DetailedStopEntity
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Mapper

class StopDomainMapper : Mapper<DetailedStopEntity, Stop> {

    override fun map(from: DetailedStopEntity): Stop {
        return Stop(from.id, from.customName()!!, Coordinate(from.latitude, from.longitude), from.customRoutes()!!)
    }

}
