package ie.dublinbuspal.domain.mapping.route

import ie.dublinbuspal.domain.repository.Mapper
import ie.dublinbuspal.database.entity.RouteEntity
import ie.dublinbuspal.service.model.route.RouteXml

class RouteEntityMapper : Mapper<RouteXml, RouteEntity> {

    override fun map(from: RouteXml): RouteEntity {
        return RouteEntity(from.id!!, from.origin!!, from.destination!!)
    }

}
