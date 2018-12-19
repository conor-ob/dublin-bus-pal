package ie.dublinbuspal.mapping.route

import ie.dublinbuspal.data.entity.DefaultRouteEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.route.RouteXml

class DefaultRouteEntityMapper : Mapper<RouteXml, DefaultRouteEntity> {

    override fun map(from: RouteXml): DefaultRouteEntity {
        return DefaultRouteEntity(from.id!!, from.origin!!, from.destination!!)
    }

}
