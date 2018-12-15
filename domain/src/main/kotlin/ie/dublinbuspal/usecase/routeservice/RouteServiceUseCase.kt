package ie.dublinbuspal.usecase.routeservice

import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(
        private val routeServiceRepository: Repository<RouteService>,
        private val stopRepository: Repository<Stop>
) {

    //TODO check routeId to determine which route service repo to use
    fun getRouteService(routeId: String): Observable<RouteService> {
        return Observable.combineLatest(
                routeServiceRepository.getById(routeId).subscribeOn(Schedulers.io()),
                stopRepository.getAll().subscribeOn(Schedulers.io()),
                BiFunction { routeService, stops -> aggregate(routeService, stops) }
        )
    }

    private fun aggregate(routeService: RouteService, stops: List<Stop>): RouteService {
        val map = stops.associateBy( {it.id()}, {it} )
        val inboundStops = routeService.inboundStopIds.map { map[it]!! } //TODO they might be null
        val outboundStops = routeService.outboundStopIds.map { map[it]!! }
        return routeService.copy(inboundStops = inboundStops, outboundStops = outboundStops)
    }

}
