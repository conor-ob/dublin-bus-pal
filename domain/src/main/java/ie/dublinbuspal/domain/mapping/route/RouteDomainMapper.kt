package ie.dublinbuspal.domain.mapping.route

import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.entity.RouteEntity
import ie.dublinbuspal.domain.model.route.Route

class RouteDomainMapper : Mapper<RouteEntity, Route> {

    override fun map(from: RouteEntity): Route {
        return Route(from.id, from.origin, from.destination)
    }

}
