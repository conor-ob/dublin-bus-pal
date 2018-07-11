package ie.dublinbuspal.domain.mapping

import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.entity.BusStopEntity
import ie.dublinbuspal.service.model.stop.BusStopXml

class BusStopEntityMapper : Mapper<BusStopXml, BusStopEntity> {

    override fun map(from: BusStopXml): BusStopEntity {
        return BusStopEntity(from.id!!, from.name!!, from.latitude!!.toDouble(), from.longitude!!.toDouble())
    }

}
