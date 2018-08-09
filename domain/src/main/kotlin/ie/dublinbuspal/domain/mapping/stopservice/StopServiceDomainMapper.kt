package ie.dublinbuspal.domain.mapping.stopservice

import ie.dublinbuspal.domain.repository.Mapper
import ie.dublinbuspal.database.entity.StopServiceEntity
import ie.dublinbuspal.domain.model.stopservice.StopService

class StopServiceDomainMapper : Mapper<StopServiceEntity, StopService> {

    override fun map(from: StopServiceEntity): StopService {
        return StopService(from.id, from.routes)
    }

}
