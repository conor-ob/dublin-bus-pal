package ie.dublinbuspal.domain.mapping.routeservice

import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.database.entity.RouteServiceEntity
import ie.dublinbuspal.domain.model.routeservice.RouteService

class RouteServiceDomainMapper : Mapper<RouteServiceEntity, RouteService> {

    override fun map(from: RouteServiceEntity): RouteService {
        return RouteService(from.id, from.name, from.origin, from.destination, from.inboundStops, from.outboundStops)
    }

}
