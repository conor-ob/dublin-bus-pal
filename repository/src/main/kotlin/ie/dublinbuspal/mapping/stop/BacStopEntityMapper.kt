package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.BacStopEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson

class BacStopEntityMapper : Mapper<StopJson, BacStopEntity> {

    override fun map(from: StopJson): BacStopEntity {
        return BacStopEntity(from.displayId!!, from.fullName!!, from.latitude!!.toDouble(), from.longitude!!.toDouble(), from.operators!![0].routes!! )
    }

}
