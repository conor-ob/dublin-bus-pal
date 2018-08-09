package ie.dublinbuspal.domain.mapping.routeservice

import ie.dublinbuspal.domain.repository.Mapper
import ie.dublinbuspal.database.entity.RouteServiceEntity
import ie.dublinbuspal.service.model.routeservice.RouteServiceInboundStopXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceOutboundStopXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml

class RouteServiceEntityMapper : Mapper<RouteServiceResponseXml, RouteServiceEntity> {

    override fun map(from: RouteServiceResponseXml): RouteServiceEntity {
        return RouteServiceEntity(name = from.routeService!!.description!!,
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
