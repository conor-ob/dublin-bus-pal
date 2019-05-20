package ie.dublinbuspal.repository.routeservice

import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.model.routeservice.RouteServiceVariant
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.KeyedRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.api.RtpiRouteService
import ie.dublinbuspal.util.Operator
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class RouteServiceRepository(
    private val dublinBusRouteServiceRepository: KeyedRepository<Pair<String, String>, RtpiRouteService>,
    private val stopRepository: Repository<Stop>
) : KeyedRepository<Pair<String, String>, RouteService> {

    override fun getById(key: Pair<String, String>): Observable<RouteService> {
        return Observable.zip(
                dublinBusRouteServiceRepository.getById(key).subscribeOn(Schedulers.newThread()),
                stopRepository.getAll().subscribeOn(Schedulers.newThread()),
                BiFunction { routeService, stops -> aggregate(routeService, stops) }
        )
    }

    private fun aggregate(
        rtpiRouteService: RtpiRouteService,
        stops: List<Stop>
    ): RouteService {
        val cache = stops.associateBy { it.id }
        val variants = mutableListOf<RouteServiceVariant>()
        for (variant in rtpiRouteService.variants) {
            variants.add(
                    RouteServiceVariant(
                            origin = variant.origin,
                            destination = variant.destination,
                            stops = variant.stopIds
                                .filter { stopId -> cache.containsKey(stopId) }
                                .map { stopId -> cache.getValue(stopId) }
                    )
            )
        }
        return RouteService(
                id = rtpiRouteService.routeId,
                operator = Operator.parse(rtpiRouteService.operatorId),
                variants = variants
        )
    }

    override fun getAll(): Observable<List<RouteService>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(key: Pair<String, String>): Observable<List<RouteService>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
