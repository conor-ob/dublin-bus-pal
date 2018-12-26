package ie.dublinbuspal.mapping.route

import ie.dublinbuspal.data.entity.DefaultRouteEntity
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.repository.Mapper

class DefaultRouteDomainMapper : Mapper<DefaultRouteEntity, DefaultRoute> {

    override fun map(from: DefaultRouteEntity): DefaultRoute {
        return DefaultRoute(from.id, from.origin, from.destination)
    }

}
