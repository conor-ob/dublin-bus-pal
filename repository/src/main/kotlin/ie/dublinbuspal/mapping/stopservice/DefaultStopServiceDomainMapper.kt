package ie.dublinbuspal.mapping.stopservice

import ie.dublinbuspal.data.entity.DefaultStopServiceEntity
import ie.dublinbuspal.model.stopservice.DefaultStopService
import ie.dublinbuspal.repository.Mapper

class DefaultStopServiceDomainMapper : Mapper<DefaultStopServiceEntity, DefaultStopService> {

    override fun map(from: DefaultStopServiceEntity): DefaultStopService {
        return DefaultStopService(from.id, from.routes)
    }

}
