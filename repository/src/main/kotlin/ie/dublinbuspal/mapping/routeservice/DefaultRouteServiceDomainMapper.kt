package ie.dublinbuspal.mapping.routeservice

import ie.dublinbuspal.data.entity.DefaultRouteServiceEntity
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.Mapper

class DefaultRouteServiceDomainMapper : Mapper<DefaultRouteServiceEntity, RouteService> {

    override fun map(from: DefaultRouteServiceEntity): RouteService {
        return RouteService(from.id, from.name, from.origin, from.destination, from.inboundStops, from.outboundStops)
    }

}
