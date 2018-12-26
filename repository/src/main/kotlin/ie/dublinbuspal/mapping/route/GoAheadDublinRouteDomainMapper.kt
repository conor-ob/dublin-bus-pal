package ie.dublinbuspal.mapping.route

import ie.dublinbuspal.data.entity.GoAheadDublinRouteEntity
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.repository.Mapper

class GoAheadDublinRouteDomainMapper : Mapper<GoAheadDublinRouteEntity, GoAheadDublinRoute> {

    override fun map(from: GoAheadDublinRouteEntity): GoAheadDublinRoute {
        return GoAheadDublinRoute(from.id, from.origin, from.destination)
    }

}
