package ie.dublinbuspal.mapping.route

import ie.dublinbuspal.data.entity.RouteEntity
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.repository.Mapper

class RouteDomainMapper : Mapper<RouteEntity, DefaultRoute> {

    override fun map(from: RouteEntity): DefaultRoute {
        return DefaultRoute(from.id, from.origin, from.destination)
    }

}
