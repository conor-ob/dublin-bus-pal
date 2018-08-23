package ie.dublinbuspal.mapping.route

import ie.dublinbuspal.data.entity.RouteEntity
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.repository.Mapper

class RouteDomainMapper : Mapper<RouteEntity, Route> {

    override fun map(from: RouteEntity): Route {
        return Route(from.id, from.origin, from.destination)
    }

}
