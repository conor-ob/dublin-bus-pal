package ie.dublinbuspal.mapping.stopservice

import ie.dublinbuspal.data.entity.StopServiceEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml

class StopServiceEntityMapper : Mapper<StopServiceResponseXml, StopServiceEntity> {

    override fun map(from: StopServiceResponseXml): StopServiceEntity {
        return StopServiceEntity(routes = from.routes.map { it.id } as List<String>) //TODO unchecked cast
    }

}
