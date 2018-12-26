package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.GoAheadDublinStopEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson

class GoAheadDublinStopEntityMapper : Mapper<StopJson, GoAheadDublinStopEntity> {

    override fun map(from: StopJson): GoAheadDublinStopEntity {
        return GoAheadDublinStopEntity(from.displayId!!, from.fullName!!, from.latitude!!.toDouble(), from.longitude!!.toDouble(), from.operators!![0].routes!! )
    }

}
