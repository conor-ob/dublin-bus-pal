package ie.dublinbuspal.mapping.route

import ie.dublinbuspal.data.entity.RouteEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.route.RouteXml

class RouteEntityMapper : Mapper<RouteXml, RouteEntity> {

    override fun map(from: RouteXml): RouteEntity {
        return RouteEntity(from.id!!, from.origin!!, from.destination!!)
    }

}