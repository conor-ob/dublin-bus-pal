package ie.dublinbuspal.mapping.routeservice

import ie.dublinbuspal.data.entity.DefaultRouteServiceEntity
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.routeservice.RouteServiceInboundStopXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceOutboundStopXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml

class DefaultRouteServiceEntityMapper : Mapper<RouteServiceResponseXml, DefaultRouteServiceEntity> {

    override fun map(from: RouteServiceResponseXml): DefaultRouteServiceEntity {
        return DefaultRouteServiceEntity(name = from.routeService!!.description!!,
                origin = from.routeService!!.origin!!,
                destination = from.routeService!!.destination!!,
                inboundStops = mapInboundStops(from.inboundStopXmls),
                outboundStops = mapOutBoundStops(from.outboundStopXmls))
    }

    private fun mapInboundStops(inboundStopXmls: List<RouteServiceInboundStopXml>): List<String> {
        return inboundStopXmls.map { it.id } as List<String>
    }

    private fun mapOutBoundStops(outboundStopXmls: List<RouteServiceOutboundStopXml>): List<String> {
        return outboundStopXmls.map { it.id } as List<String>
    }

}
