package ie.dublinbuspal.repository.mapping.stop

import ie.dublinbuspal.data.entity.SmartDublinStopServiceEntity
import ie.dublinbuspal.domain.model.stopservice.SmartDublinStopService
import ie.dublinbuspal.domain.repository.Mapper

class SmartDublinStopServiceDomainMapper : Mapper<SmartDublinStopServiceEntity, SmartDublinStopService> {

    override fun map(from: SmartDublinStopServiceEntity): SmartDublinStopService {
        return SmartDublinStopService()
    }

}
