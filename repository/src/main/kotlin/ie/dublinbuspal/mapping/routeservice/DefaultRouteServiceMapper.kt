package ie.dublinbuspal.mapping.routeservice

//class DefaultRouteServiceMapper : Mapper<RouteServiceResponseXml, DefaultRouteService> {
//
//    override fun map(from: RouteServiceResponseXml): DefaultRouteService {
//        return DefaultRouteService(
//                from.routeService!!.description!!,
//                from.routeService!!.origin!!,
//                from.routeService!!.destination!!,
//                mapInboundStops(from.inboundStopXmls),
//                mapOutBoundStops(from.outboundStopXmls),
//                emptyList(),
//                emptyList()
//        )
//    }
//
//    private fun mapInboundStops(inboundStopXmls: List<RouteServiceInboundStopXml>): List<String> {
//        return inboundStopXmls.map { it.id!! }
//    }
//
//    private fun mapOutBoundStops(outboundStopXmls: List<RouteServiceOutboundStopXml>): List<String> {
//        return outboundStopXmls.map { it.id!! }
//    }
//
//}
