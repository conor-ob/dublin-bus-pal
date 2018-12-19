package ie.dublinbuspal.repository.stop

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.stop.DefaultStop
import ie.dublinbuspal.model.stop.DublinBusGoAheadDublinStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteStopRepository
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

class StopRepository(
        private val defaultStopRepository: Repository<DefaultStop>,
        private val dublinBusStopRepository: Repository<DublinBusGoAheadDublinStop>,
        private val goAheadDublinStopRepository: Repository<DublinBusGoAheadDublinStop>,
        private val favouriteStopRepository: FavouriteStopRepository<FavouriteStop>
) : Repository<Stop> {

    override fun getAll(): Observable<List<Stop>> {
        return Observable.combineLatest(
                defaultStopRepository.getAll().startWith(emptyList<DefaultStop>()).subscribeOn(Schedulers.io()),
                dublinBusStopRepository.getAll().startWith(emptyList<DublinBusGoAheadDublinStop>()).subscribeOn(Schedulers.io()),
                goAheadDublinStopRepository.getAll().startWith(emptyList<DublinBusGoAheadDublinStop>()).subscribeOn(Schedulers.io()),
                favouriteStopRepository.getAll().startWith(emptyList<FavouriteStop>()).subscribeOn(Schedulers.io()),
                Function4 { defaultStops, dublinBusStops, goAheadDublinStops, favouriteStops ->
                    aggregate(defaultStops, dublinBusStops, goAheadDublinStops, favouriteStops)
                }
        )
    }

    override fun getById(id: String): Observable<Stop> {
        return getAll().map { stops -> findMatching(id, stops) }.distinctUntilChanged()
    }

    private fun findMatching(id: String, stops: List<Stop>): Stop {
        for (stop in stops) {
            if (id == stop.id()) {
                return stop
            }
        }
        return Stop(defaultId = "-1")
    }

    private fun aggregate(
            defaultStops: List<DefaultStop>,
            dublinBusStops: List<DublinBusGoAheadDublinStop>,
            goAheadDublinStops: List<DublinBusGoAheadDublinStop>,
            favouriteStops: List<FavouriteStop>
    ): List<Stop> {
        val aggregatedStops = mutableMapOf<String, Stop>()
        for (stop in defaultStops) {
            aggregatedStops[stop.id] = Stop(defaultId = stop.id, defaultName = stop.name, defaultCoordinate = stop.coordinate)
        }
        for (stop in dublinBusStops) {
            val aggregatedStop = aggregatedStops[stop.id]
            if (aggregatedStop == null) {
                aggregatedStops[stop.id] = Stop(dublinBusId = stop.id, dublinBusName = stop.name, dublinBusCoordinate = stop.coordinate, dublinBusRoutes = stop.routes)
            } else {
                aggregatedStops[stop.id] = aggregatedStop.copy(dublinBusId = stop.id, dublinBusName = stop.name, dublinBusCoordinate = stop.coordinate, dublinBusRoutes = stop.routes)
            }
        }
        for (stop in goAheadDublinStops) {
            val aggregatedStop = aggregatedStops[stop.id]
            if (aggregatedStop == null) {
                aggregatedStops[stop.id] = Stop(goAheadDublinId = stop.id, goAheadDublinName = stop.name, goAheadDublinCoordinate = stop.coordinate, goAheadDublinRoutes = stop.routes)
            } else {
                aggregatedStops[stop.id] = aggregatedStop.copy(goAheadDublinId = stop.id, goAheadDublinName = stop.name, goAheadDublinCoordinate = stop.coordinate, goAheadDublinRoutes = stop.routes)
            }
        }
        for (stop in favouriteStops) {
            val aggregatedStop = aggregatedStops[stop.id]
            if (aggregatedStop != null) {
                aggregatedStops[stop.id] = aggregatedStop.copy(favouriteName = stop.name, favouriteRoutes = stop.routes)
            }
        }
        return aggregatedStops.values.toList()
    }

    override fun getAllById(id: String): Observable<List<Stop>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
