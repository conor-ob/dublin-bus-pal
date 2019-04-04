package ie.dublinbuspal.usecase.routeservice

import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.routeservice.DefaultRouteService
import ie.dublinbuspal.model.routeservice.GoAheadDublinRouteService
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.Operator
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(
//        private val routeRepository: Repository<Route>,
        private val stopRepository: Repository<Stop>
//        private val defaultRouteServiceRepository: Repository<DefaultRouteService>,
//        private val goAheadDublinRouteServiceRepository: Repository<GoAheadDublinRouteService>
) {

    fun getRouteService(routeId: String): Observable<RouteService> {
//        return routeRepository.getById(routeId)
//                .flatMap { getRouteServiceByOperator(it) }
        TODO()
    }

    private fun getRouteServiceByOperator(route: Route): Observable<RouteService> {
        TODO()
//        if (route.operator == Operator.DUBLIN_BUS) {
//            return Observable.combineLatest(
//                    defaultRouteServiceRepository.getById(route.id).subscribeOn(Schedulers.io()),
//                    stopRepository.getAll().subscribeOn(Schedulers.io()),
//                    BiFunction { defaultRouteService, stops -> aggregateDefaultRouteService(route, defaultRouteService, stops) }
//            )
//        }
//        return goAheadDublinRouteServiceRepository.getById(route.id).subscribeOn(Schedulers.io())
//                .map { aggregateGoAheadDublinRouteService(route, it) }
    }

    private fun aggregateDefaultRouteService(route: Route, defaultRouteService: DefaultRouteService, stops: List<Stop>): RouteService {
        val map = stops.associateBy({ it.id() }, { it })
        val inboundStops = defaultRouteService.inboundStopIds.map { map[it]!! } //TODO they might be null
        val outboundStops = defaultRouteService.outboundStopIds.map { map[it]!! }
        return RouteService(route.id, defaultRouteService.origin, defaultRouteService.destination,
                defaultRouteService.inboundStopIds, defaultRouteService.outboundStopIds, inboundStops, outboundStops)
    }

    private fun aggregateGoAheadDublinRouteService(route: Route, goAheadDublinRouteService: GoAheadDublinRouteService): RouteService {
        val startAndEnd = listOf(route.origin.trim(), route.destination.trim()).sorted()

        val filtered = goAheadDublinRouteService.variants.filter { startAndEnd == listOf(it.origin.trim(), it.destination.trim()).sorted() }

        var inboundStopIds = emptyList<String>()
        var outboundStopIds = emptyList<String>()
        var inboundStops = emptyList<Stop>()
        var outboundStops = emptyList<Stop>()
        if (filtered.size == 1) {
            outboundStops = filtered[0].stops
            outboundStopIds = filtered[0].stops.map { it.id() }
        } else if (filtered.size > 1) {
            outboundStops = filtered[0].stops
            outboundStopIds = filtered[0].stops.map { it.id() }
            inboundStops = filtered[1].stops
            inboundStopIds = filtered[1].stops.map { it.id() }
        }

        return RouteService(route.id, route.origin, route.destination, inboundStopIds, outboundStopIds, inboundStops, outboundStops)
    }

}
