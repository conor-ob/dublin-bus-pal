package ie.dublinbuspal.usecase.nearby

import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.PreferencesRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.LocationUtils
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class NearbyStopsUseCase @Inject constructor(private val stopRepository: Repository<List<Stop>, Any>,
                                             private val preferences: PreferencesRepository) {

    fun getNearbyBusStops(coordinate: Coordinate): Observable<SortedMap<Double, Stop>> {
        return stopRepository.get(0)
                .map { filter(it, coordinate) }
    }

    private fun filter(stops: List<Stop>, coordinate: Coordinate): SortedMap<Double, Stop> {
        val sorted = TreeMap<Double, Stop>()
        for (busStop in stops) {
            sorted[LocationUtils.haversineDistance(coordinate, busStop.coordinate)] = busStop
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
