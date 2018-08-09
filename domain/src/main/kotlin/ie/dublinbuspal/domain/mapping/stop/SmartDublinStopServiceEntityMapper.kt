package ie.dublinbuspal.domain.mapping.stop

import ie.dublinbuspal.domain.repository.Mapper
import ie.dublinbuspal.data.entity.SmartDublinStopServiceEntity
import ie.dublinbuspal.service.model.stop.OperatorJson
import ie.dublinbuspal.service.model.stop.StopJson

class SmartDublinStopServiceEntityMapper : Mapper<StopJson, SmartDublinStopServiceEntity> {

    override fun map(from: StopJson): SmartDublinStopServiceEntity {
        return SmartDublinStopServiceEntity(from.id!!, mapRoutes(from.operators!!))
    }

    private fun mapRoutes(operators: List<OperatorJson>): List<String> {
        val operator = operators[0]
        return operator.routes!!
    }

}
