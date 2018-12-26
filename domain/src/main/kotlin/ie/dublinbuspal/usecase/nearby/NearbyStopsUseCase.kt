package ie.dublinbuspal.usecase.nearby

import ie.dublinbuspal.location.LocationProvider
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.PreferencesRepository
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.LocationUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject

class NearbyStopsUseCase @Inject constructor(
        private val stopsRepository: Repository<Stop>,
        private val locationProvider: LocationProvider
) {

    fun getNearbyBusStops(coordinate: Coordinate): Observable<SortedMap<Double, Stop>> {
        return stopsRepository.getAll()
                .map { filter(it, coordinate) }
    }

    private fun filter(stops: List<Stop>, coordinate: Coordinate): SortedMap<Double, Stop> {
        val sorted = TreeMap<Double, Stop>()
        for (busStop in stops) {
            sorted[LocationUtils.haversineDistance(coordinate, busStop.coordinate())] = busStop
        }
        return CollectionUtils.headMap(sorted, 30)
    }

    fun getLocationUpdates(): Observable<Coordinate> {
        return Observable.concat(locationProvider.getLastKnownLocation(), locationProvider.getLocationUpdates())
    }

//    fun getLastLocation(): Observable<Pair<Coordinate, Float>> {
//        return preferences.getLastLocation()
//    }
//
//    fun saveLastLocation(location: Pair<Coordinate, Float>): Completable {
//        return preferences.saveLastLocation(location)
//    }

}
