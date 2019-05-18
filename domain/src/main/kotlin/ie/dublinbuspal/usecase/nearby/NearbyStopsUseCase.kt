package ie.dublinbuspal.usecase.nearby

import ie.dublinbuspal.location.LocationProvider
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.Coordinate
import io.reactivex.Observable
import javax.inject.Inject

class NearbyStopsUseCase @Inject constructor(
    private val stopsRepository: Repository<Stop>,
    private val locationProvider: LocationProvider
) {

    private val kdTree = TwoDimensionalKdTree()

    fun getNearbyBusStops(coordinate: Coordinate): Observable<List<Stop>> {
        if (kdTree.isEmpty()) {
            return preloadKdTree(coordinate)
        }
        return Observable.just(findNearest(coordinate))
    }

    private fun preloadKdTree(coordinate: Coordinate): Observable<List<Stop>> {
        return stopsRepository.getAll()
            .map { stops ->
                kdTree.insert(stops)
                findNearest(coordinate)
            }
    }

    private fun findNearest(coordinate: Coordinate): List<Stop> {
        return kdTree.nearest(coordinate, 30)
    }

    fun getLocationUpdates(): Observable<Coordinate> {
        return Observable.concat(locationProvider.getLastKnownLocation(), locationProvider.getLocationUpdates())
    }

}
