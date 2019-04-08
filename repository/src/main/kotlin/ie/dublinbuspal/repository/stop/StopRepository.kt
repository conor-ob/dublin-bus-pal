package ie.dublinbuspal.repository.stop

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteStopRepository
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class StopRepository(
        private val dublinBusStopRepository: Repository<DublinBusStop>,
        private val favouriteStopRepository: FavouriteStopRepository<FavouriteStop>
) : Repository<Stop> {

    //TODO do I need a different data class with favourite info?
    private var cache = emptyMap<String, Stop>()

    override fun getAll(): Observable<List<Stop>> {
        return Observable.combineLatest(
                dublinBusStopRepository.getAll().subscribeOn(Schedulers.io()),
                favouriteStopRepository.getAll().subscribeOn(Schedulers.io()),
                BiFunction { dublinBusStops, favouriteStops ->
                    aggregate(dublinBusStops, favouriteStops)
                }
        )
    }

    override fun getById(id: String): Observable<Stop> {
        return Observable.just(cache[id])
    }

    private fun aggregate(
            dublinBusStops: List<DublinBusStop>,
            favouriteStops: List<FavouriteStop>
    ): List<Stop> {
        val aggregatedStops = mutableMapOf<String, Stop>()
        for (stop in dublinBusStops) {
            aggregatedStops[stop.id] = Stop(id = stop.id, name = stop.name, coordinate = stop.coordinate, operators = stop.operators, routes = stop.routes)
        }
        for (stop in favouriteStops) {
            val aggregatedStop = aggregatedStops[stop.id]
            if (aggregatedStop != null) {
                aggregatedStops[stop.id] = aggregatedStop.copy(favouriteName = stop.name, favouriteRoutes = stop.routes.toSet()) //TODO
            }
        }
        cache = aggregatedStops
        return aggregatedStops.values.toList()
    }

    override fun getAllById(id: String): Observable<List<Stop>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
