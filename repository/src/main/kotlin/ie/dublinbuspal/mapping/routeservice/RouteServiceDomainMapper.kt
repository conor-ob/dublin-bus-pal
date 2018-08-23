package ie.dublinbuspal.mapping.routeservice

import ie.dublinbuspal.data.entity.RouteServiceEntity
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.Mapper

class RouteServiceDomainMapper : Mapper<RouteServiceEntity, RouteService> {

    override fun map(from: RouteServiceEntity): RouteService {
        return RouteService(from.id, from.name, from.origin, from.destination, from.inboundStops, from.outboundStops)
    }

}
