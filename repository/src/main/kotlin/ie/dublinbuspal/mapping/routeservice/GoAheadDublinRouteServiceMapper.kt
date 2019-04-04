package ie.dublinbuspal.mapping.routeservice

//class GoAheadDublinRouteServiceMapper : Mapper<RouteInformationResponseJson, GoAheadDublinRouteService> {
//
//    override fun map(from: RouteInformationResponseJson): GoAheadDublinRouteService {
//        return GoAheadDublinRouteService(mapVariants(from.results))
//    }
//
//    private fun mapVariants(results: List<RouteInformationJson>): List<GoAheadDublinRouteServiceVariant> {
//        return results.map { GoAheadDublinRouteServiceVariant(it.origin!!, it.destination!!, mapStops(it.stops)) }
//    }
//
//    private fun mapStops(stops: List<StopJson>): List<Stop> {
//        TODO()
//    }
//
//}
