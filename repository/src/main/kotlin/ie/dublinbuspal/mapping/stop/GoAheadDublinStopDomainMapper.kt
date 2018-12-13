package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.GoAheadDublinStopEntity
import ie.dublinbuspal.model.stop.DublinBusGoAheadDublinStop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.util.Coordinate

class GoAheadDublinStopDomainMapper : Mapper<GoAheadDublinStopEntity, DublinBusGoAheadDublinStop> {

    override fun map(from: GoAheadDublinStopEntity): DublinBusGoAheadDublinStop {
        return DublinBusGoAheadDublinStop(from.id, from.name, Coordinate(from.latitude, from.longitude), from.routes)
    }

}
