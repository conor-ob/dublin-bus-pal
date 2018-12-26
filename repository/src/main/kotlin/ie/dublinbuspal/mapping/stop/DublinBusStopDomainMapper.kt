package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.DublinBusStopEntity
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.util.Coordinate

class DublinBusStopDomainMapper : Mapper<DublinBusStopEntity, DublinBusStop> {

    override fun map(from: DublinBusStopEntity): DublinBusStop {
        return DublinBusStop(from.id, from.name, Coordinate(from.latitude, from.longitude), from.routes)
    }

}
