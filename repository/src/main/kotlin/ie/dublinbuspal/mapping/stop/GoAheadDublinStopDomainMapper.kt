package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.GoAheadDublinStopEntity
import ie.dublinbuspal.model.stop.GoAheadDublinStop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.util.Coordinate

class GoAheadDublinStopDomainMapper : Mapper<GoAheadDublinStopEntity, GoAheadDublinStop> {

    override fun map(from: GoAheadDublinStopEntity): GoAheadDublinStop {
        return GoAheadDublinStop(from.id, from.name, Coordinate(from.latitude, from.longitude), from.routes)
    }

}
