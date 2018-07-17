package ie.dublinbuspal.domain.usecase.nearby

import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.base.PreferencesRepository
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.base.util.CollectionUtils
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.domain.util.LocationUtils
import ie.dublinbuspal.service.model.stop.StopsRequestBodyXml
import ie.dublinbuspal.service.model.stop.StopsRequestRootXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class NearbyStopsUseCase @Inject constructor(private val repository: Repository<List<Stop>, StopsRequestXml>,
                                             private val preferences: PreferencesRepository) {

    private val key: StopsRequestXml by lazy {
        val root = StopsRequestRootXml()
        val body = StopsRequestBodyXml(root)
        return@lazy StopsRequestXml(body)
    }

    fun getNearbyBusStops(coordinate: Coordinate): Observable<SortedMap<Double, Stop>> {
        return repository.get(key)
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
