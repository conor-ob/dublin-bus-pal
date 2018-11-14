package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.BacStopEntity
import ie.dublinbuspal.model.stop.SmartDublinStop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.util.Coordinate

class BacStopDomainMapper : Mapper<BacStopEntity, SmartDublinStop> {

    override fun map(from: BacStopEntity): SmartDublinStop {
        return SmartDublinStop(from.id, from.name, Coordinate(from.latitude, from.longitude), from.routes)
    }

}
