package ie.dublinbuspal.repository.route

import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.AlphanumComparator
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*

class RouteRepository(
        private val defaultRouteRepository: Repository<DefaultRoute>,
        private val goAheadDublinRouteRepository: Repository<GoAheadDublinRoute>
) : Repository<Route> {

    override fun getAll(): Observable<List<Route>> {
        return Observable.combineLatest(
                defaultRouteRepository.getAll().startWith(emptyList<DefaultRoute>()).subscribeOn(Schedulers.io()),
                goAheadDublinRouteRepository.getAll().startWith(emptyList<GoAheadDublinRoute>()).subscribeOn(Schedulers.io()),
                BiFunction { defaultRoutes, goAheadDublinRoutes -> aggregate(defaultRoutes, goAheadDublinRoutes) }
        )
    }

    private fun aggregate(defaultRoutes: List<DefaultRoute>, goAheadDublinRoutes: List<GoAheadDublinRoute>): List<Route> {
        val routes = mutableListOf<Route>()

        defaultRoutes.map { Route(it.id, it.origin, it.destination, it.operator) }
                .forEach { routes.add(it) }

        goAheadDublinRoutes.map { Route(it.id, it.origin, it.destination, it.operator) }
                .forEach { routes.add(it) }

        routes.sortWith(Comparator { thisRoute, thatRoute -> AlphanumComparator.getInstance().compare(thisRoute.id, thatRoute.id) } )
        return routes
    }

    override fun getById(id: String): Observable<Route> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<Route>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
