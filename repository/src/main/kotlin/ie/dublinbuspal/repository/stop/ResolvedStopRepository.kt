package ie.dublinbuspal.repository.stop

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.model.stop.SmartDublinStop
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.FavouriteRepository
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.Function4

class ResolvedStopRepository(
        private val stopRepository: Repository<List<Stop>, Any>,
        private val bacStopRepository: Repository<List<SmartDublinStop>, Any>,
        private val gadStopRepository: Repository<List<SmartDublinStop>, Any>,
        private val favouritesRepository: FavouriteRepository<List<FavouriteStop>, Any>
) : Repository<List<ResolvedStop>, Any> {

    override fun get(key: Any): Observable<List<ResolvedStop>> {
        return Observable.zip(
                stopRepository.get(0),
                bacStopRepository.get(0),
                gadStopRepository.get(0),
                favouritesRepository.get(0),
                Function4 { s1, s2, s3, s4 -> resolve(s1, s2, s3, s4) }
        )
    }

    override fun fetch(key: Any): Observable<List<ResolvedStop>> {
        return Observable.zip(
                stopRepository.fetch(0),
                bacStopRepository.fetch(0),
                gadStopRepository.fetch(0),
                favouritesRepository.get(0),
                Function4 { s1, s2, s3, s4 -> resolve(s1, s2, s3, s4) }
        )
    }

    private fun resolve(
            stops: List<Stop>,
            bacStops: List<SmartDublinStop>,
            gadStops: List<SmartDublinStop>,
            favouriteStops: List<FavouriteStop>
    ): List<ResolvedStop> {
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
        return resolvedStops.values.toList()
    }

}
