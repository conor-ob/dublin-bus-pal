package ie.dublinbuspal.repository.stop

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.repository.FavouriteStopRepository
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class StopRepository(
        private val dublinBusStopRepository: Repository<DublinBusStop>,
        private val favouriteStopRepository: FavouriteStopRepository<FavouriteStop>
) : Repository<DublinBusStop> {

    //TODO do I need a different data class with favourite info?

    override fun getAll(): Observable<List<DublinBusStop>> {
        return Observable.combineLatest(
                dublinBusStopRepository.getAll().subscribeOn(Schedulers.io()),
                favouriteStopRepository.getAll().subscribeOn(Schedulers.io()),
                BiFunction { dublinBusStops, favouriteStops ->
                    aggregate(dublinBusStops, favouriteStops)
                }
        )
    }

    override fun getById(id: String): Observable<DublinBusStop> {
        return getAll()
                .map { stops -> findMatching(id, stops) }
                .filter { stop -> stop.id != "-1" }
                .distinctUntilChanged()
    }

    private fun findMatching(id: String, stops: List<DublinBusStop>): DublinBusStop {
        for (stop in stops) {
            if (id == stop.id) {
                return stop
            }
        }
//        return DublinBusStop(id = "-1")
        throw RuntimeException()
    }

    private fun aggregate(
            dublinBusStops: List<DublinBusStop>,
            favouriteStops: List<FavouriteStop>
    ): List<DublinBusStop> {
        val dublinBusStopsById = mutableMapOf<String, DublinBusStop>()
        for (dublinBusStop in dublinBusStops) {
            dublinBusStopsById[dublinBusStop.id] = dublinBusStop
        }
        for (favouriteStop in favouriteStops) {
            val cachedStop = dublinBusStopsById[favouriteStop.id]
            if (cachedStop != null) {
                dublinBusStopsById[favouriteStop.id] = cachedStop.copy(name = favouriteStop.name)
            }
        }
        return dublinBusStopsById.values
//                .filter { it.routes().isNotEmpty() }
                .toList()
    }

    override fun getAllById(id: String): Observable<List<DublinBusStop>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
