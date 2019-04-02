package ie.dublinbuspal.repository.route

import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.AlphanumComparator
import ie.dublinbuspal.util.Operator
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
                defaultRouteRepository.getAll().subscribeOn(Schedulers.io()),
                goAheadDublinRouteRepository.getAll().subscribeOn(Schedulers.io()),
                BiFunction { defaultRoutes, goAheadDublinRoutes -> aggregate(defaultRoutes, goAheadDublinRoutes) }
        )
    }

    override fun getById(id: String): Observable<Route> {
        return getAll()
                .map { routes -> findMatching(id, routes) }
                .filter { route -> route.id != "-1" }
                .distinctUntilChanged()
    }

    private fun findMatching(id: String, routes: List<Route>): Route {
        for (route in routes) {
            if (id == route.id) {
                return route
            }
        }
        return Route(id = "-1", origin = "", destination = "", operator = Operator.DEFAULT)
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

    override fun getAllById(id: String): Observable<List<Route>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
