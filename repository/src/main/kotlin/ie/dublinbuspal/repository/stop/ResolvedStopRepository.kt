package ie.dublinbuspal.repository.stop

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.model.stop.SmartDublinStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteRepository
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

class ResolvedStopRepository(
        private val stopRepository: Repository<Stop>,
        private val bacStopRepository: Repository<SmartDublinStop>,
        private val gadStopRepository: Repository<SmartDublinStop>,
        private val favouritesRepository: FavouriteRepository<FavouriteStop>
) : Repository<ResolvedStop> {

    private var resolved = false
    private var resolvedCache = emptyList<ResolvedStop>()

    override fun getAll(): Observable<List<ResolvedStop>> {
        if (resolved && resolvedCache.isNotEmpty()) {
            return Observable.just(resolvedCache)
        }
        return Observable.combineLatest(
                stopRepository.getAll().startWith(emptyList<Stop>()).subscribeOn(Schedulers.io()),
                bacStopRepository.getAll().startWith(emptyList<SmartDublinStop>()).subscribeOn(Schedulers.io()),
                gadStopRepository.getAll().startWith(emptyList<SmartDublinStop>()).subscribeOn(Schedulers.io()),
                favouritesRepository.getAll().startWith(emptyList<FavouriteStop>()).subscribeOn(Schedulers.computation()),
                Function4 { s1, s2, s3, s4 -> resolve(s1, s2, s3, s4) }
        )
    }

    override fun getAllById(id: String): Observable<List<ResolvedStop>> {
        throw UnsupportedOperationException()
    }

    override fun getById(id: String): Observable<ResolvedStop> {
        throw UnsupportedOperationException()
    }

    private fun resolve(
            stops: List<Stop>,
            bacStops: List<SmartDublinStop>,
            gadStops: List<SmartDublinStop>,
            favouriteStops: List<FavouriteStop>
    ): List<ResolvedStop> {
        //TODO need to do some synchronization here
        return if (stops.size > 1 && bacStops.size > 1 && gadStops.size > 1) {
            resolveInternal(stops, bacStops, gadStops, favouriteStops, true)
        } else {
            resolveInternal(stops, bacStops, gadStops, favouriteStops, false)
        }
    }

    private fun resolveInternal(stops: List<Stop>, bacStops: List<SmartDublinStop>, gadStops: List<SmartDublinStop>, favouriteStops: List<FavouriteStop>, finalResolve: Boolean): List<ResolvedStop> {
        val resolvedStops = mutableMapOf<String, ResolvedStop>()
        for (stop in stops) {
            resolvedStops[stop.id] = ResolvedStop(id = stop.id, name = stop.name, coordinate = stop.coordinate)
        }
        for (stop in bacStops) {
            val resolvedStop = resolvedStops[stop.id]
            if (resolvedStop == null) {
                resolvedStops[stop.id] = ResolvedStop(bacId = stop.id, bacName = stop.name, bacCoordinate = stop.coordinate, bacRoutes = stop.routes)
            } else {
                resolvedStops[stop.id] = resolvedStop.copy(bacId = stop.id, bacName = stop.name, bacCoordinate = stop.coordinate, bacRoutes = stop.routes)
            }
        }
        for (stop in gadStops) {
            val resolvedStop = resolvedStops[stop.id]
            if (resolvedStop == null) {
                resolvedStops[stop.id] = ResolvedStop(gadId = stop.id, gadName = stop.name, gadCoordinate = stop.coordinate, gadRoutes = stop.routes)
            } else {
                resolvedStops[stop.id] = resolvedStop.copy(gadId = stop.id, gadName = stop.name, gadCoordinate = stop.coordinate, gadRoutes = stop.routes)
            }
        }
        for (stop in favouriteStops) {
            val resolvedStop = resolvedStops[stop.id]
            if (resolvedStop == null) {
                // uh oh!
            } else {
                resolvedStops[stop.id] = resolvedStop.copy(favouriteName = stop.name, favouriteRoutes = stop.routes)
            }
        }
        resolved = finalResolve
        return resolvedStops.values.toList()
    }

}
