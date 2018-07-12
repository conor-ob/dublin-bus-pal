package ie.dublinbuspal.domain.mapping.stop

import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.entity.BusStopEntity
import ie.dublinbuspal.domain.model.stop.BusStop

class BusStopDomainMapper : Mapper<BusStopEntity, BusStop> {

    override fun map(from: BusStopEntity): BusStop {
        return BusStop(from.id, from.name, Coordinate(from.latitude, from.longitude))
    }

}
