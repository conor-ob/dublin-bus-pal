package ie.dublinbuspal.usecase.nearby

import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.repository.PreferencesRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.LocationUtils
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class NearbyStopsUseCase @Inject constructor(private val stopsRepository: Repository<ResolvedStop>,
                                             private val preferences: PreferencesRepository) {

    fun getNearbyBusStops(coordinate: Coordinate): Observable<SortedMap<Double, ResolvedStop>> {
        return stopsRepository.getAll()
                .map { filter(it, coordinate) }
    }

    private fun filter(stops: List<ResolvedStop>, coordinate: Coordinate): SortedMap<Double, ResolvedStop> {
        val sorted = TreeMap<Double, ResolvedStop>()
        for (busStop in stops) {
            sorted[LocationUtils.haversineDistance(coordinate, busStop.coordinate())] = busStop
        }
        return CollectionUtils.headMap(sorted, 30)
    }

    fun getLastLocation(): Observable<Pair<Coordinate, Float>> {
        return preferences.getLastLocation()
    }

    fun saveLastLocation(location: Pair<Coordinate, Float>): Completable {
        return preferences.saveLastLocation(location)
    }

}
