package ie.dublinbuspal.mapping.stopservice

import ie.dublinbuspal.data.entity.StopServiceEntity
import ie.dublinbuspal.model.stopservice.StopService
import ie.dublinbuspal.repository.Mapper

class StopServiceDomainMapper : Mapper<StopServiceEntity, StopService> {

    override fun map(from: StopServiceEntity): StopService {
        return StopService(from.id, from.routes)
    }

}
