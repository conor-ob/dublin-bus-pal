package ie.dublinbuspal.domain.usecase

import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.BusStop
import ie.dublinbuspal.domain.util.LocationUtils
import ie.dublinbuspal.service.model.busstops.BusStopsRequestBodyXml
import ie.dublinbuspal.service.model.busstops.BusStopsRequestRootXml
import ie.dublinbuspal.service.model.busstops.BusStopsRequestXml
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class NearbyBusStopsUseCase @Inject constructor(private val repository: Repository<List<BusStop>, BusStopsRequestXml>) {

    private val key: BusStopsRequestXml by lazy {
        val root = BusStopsRequestRootXml()
        val body = BusStopsRequestBodyXml(root)
        return@lazy BusStopsRequestXml(body)
    }

    fun getNearbyBusStops(coordinate: Coordinate): Observable<SortedMap<Double, BusStop>> {
        return repository.get(key)
                .map { filter(it, coordinate) }
    }

    private fun filter(busStops: List<BusStop>, coordinate: Coordinate): SortedMap<Double, BusStop> {
        val sorted = TreeMap<Double, BusStop>()
        val filtered = TreeMap<Double, BusStop>()

        for (busStop in busStops) {
            sorted[LocationUtils.haversineDistance(coordinate, busStop.coordinate)] = busStop
        }

        var i = 0
        val iterator = sorted.entries.iterator()
        while (iterator.hasNext() && i < 30) {
            val next = iterator.next()
            filtered[next.key] = next.value
            i++
        }

        return filtered
    }

}
