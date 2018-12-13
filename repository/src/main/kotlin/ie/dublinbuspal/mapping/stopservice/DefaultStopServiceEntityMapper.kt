package ie.dublinbuspal.mapping.stopservice

import ie.dublinbuspal.data.entity.DefaultStopServiceEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml

class DefaultStopServiceEntityMapper : Mapper<StopServiceResponseXml, DefaultStopServiceEntity> {

    override fun map(from: StopServiceResponseXml): DefaultStopServiceEntity {
        return DefaultStopServiceEntity(routes = from.routes.map { it.id } as List<String>) //TODO unchecked cast
    }

}
