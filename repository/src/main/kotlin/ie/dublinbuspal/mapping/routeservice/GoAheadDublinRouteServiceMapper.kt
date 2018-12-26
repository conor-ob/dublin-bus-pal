package ie.dublinbuspal.mapping.routeservice

import ie.dublinbuspal.model.routeservice.GoAheadDublinRouteService
import ie.dublinbuspal.model.routeservice.GoAheadDublinRouteServiceVariant
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.routeservice.RouteInformationJson
import ie.dublinbuspal.service.model.routeservice.RouteInformationResponseJson
import ie.dublinbuspal.service.model.stop.StopJson
import ie.dublinbuspal.util.Coordinate

class GoAheadDublinRouteServiceMapper : Mapper<RouteInformationResponseJson, GoAheadDublinRouteService> {

    override fun map(from: RouteInformationResponseJson): GoAheadDublinRouteService {
        return GoAheadDublinRouteService(mapVariants(from.results))
    }

    private fun mapVariants(results: List<RouteInformationJson>): List<GoAheadDublinRouteServiceVariant> {
        return results.map { GoAheadDublinRouteServiceVariant(it.origin!!, it.destination!!, mapStops(it.stops)) }
    }

    private fun mapStops(stops: List<StopJson>): List<Stop> {
        return stops.map { Stop(defaultId = it.displayId, defaultName = it.fullName, defaultCoordinate = Coordinate(it.latitude!!.toDouble(), it.longitude!!.toDouble())) }
    }

}
