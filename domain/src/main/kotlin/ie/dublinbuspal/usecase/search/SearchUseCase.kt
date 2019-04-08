package ie.dublinbuspal.usecase.search

import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.AlphanumComparator
import ie.dublinbuspal.util.CollectionUtils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class SearchUseCase @Inject constructor(
        private val stopsRepository: Repository<Stop>,
        private val routesRepository: Repository<Route>
) {

    fun search(value: String?): Observable<List<Any>> {
        return Observable.combineLatest(
                stopsRepository.getAll().map { searchStops(value, it) }.subscribeOn(Schedulers.io()),
                routesRepository.getAll().map { searchRoutes(value, it) }.subscribeOn(Schedulers.io()),
                BiFunction { stops, routes -> aggregate(stops, routes) }
        )
    }

    private fun aggregate(stops: List<Stop>, routes: List<Route>): List<Any> {
        val searchResult = mutableListOf<Any>()

        if (!CollectionUtils.isNullOrEmpty(routes)) {
            if (routes.size == 1) {
                searchResult.add("Found 1 route")
            } else {
                searchResult.add(String.format(Locale.UK, "Found %s routes", routes.size))
            }
            val result = routes.toMutableList()
            result.sortWith(Comparator { r1, r2 -> AlphanumComparator.getInstance().compare(r1.id, r2.id) })
            searchResult.addAll(result)
        }

        if (!CollectionUtils.isNullOrEmpty(stops)) {
            if (stops.size == 1) {
                searchResult.add("Found 1 bus stop")
            } else {
                searchResult.add(String.format(Locale.UK, "Found %s bus stops", stops.size))
            }
            val result = stops.toMutableList()
            result.sortWith(Comparator { s1, s2 -> AlphanumComparator.getInstance().compare(s1.id(), s2.id()) })
            searchResult.addAll(result)
        }

        return searchResult
    }

    private fun searchStops(value: String?, stops: List<Stop>): List<Stop> {
        val result = mutableListOf<Stop>()
        for (stop in stops) {
            if (String.format("%s %s", stop.id(), stop.name()).toLowerCase().contains(value!!)) {
                result.add(stop)
            }
        }
        return result
    }

    private fun searchRoutes(value: String?, routes: List<Route>): List<Route> {
        val result = mutableListOf<Route>()
        for (route in routes) {
            if (String.format("%s %s %s %s", route.id, route.origin, route.destination, route.operator.displayName).toLowerCase().contains(value!!)) {
                result.add(route)
            }
        }
        return result
    }

}
