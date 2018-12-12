package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.GadStopEntity
import ie.dublinbuspal.model.stop.SmartDublinStop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.util.Coordinate

class GadStopDomainMapper : Mapper<GadStopEntity, SmartDublinStop> {

    override fun map(from: GadStopEntity): SmartDublinStop {
        return SmartDublinStop(from.id, from.name, Coordinate(from.latitude, from.longitude), from.routes)
    }

}
