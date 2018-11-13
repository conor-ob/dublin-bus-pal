package ie.dublinbuspal.mapping.stop

import ie.dublinbuspal.data.entity.SmartDublinStopServiceEntity
import ie.dublinbuspal.model.stopservice.SmartDublinStopService
import ie.dublinbuspal.repository.Mapper

class SmartDublinStopServiceDomainMapper : Mapper<SmartDublinStopServiceEntity, SmartDublinStopService> {

    override fun map(from: SmartDublinStopServiceEntity): SmartDublinStopService {
        return SmartDublinStopService()
    }

}
