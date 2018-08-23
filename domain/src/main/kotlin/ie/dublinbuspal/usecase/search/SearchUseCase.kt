package ie.dublinbuspal.usecase.search

import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val stopsRepository: Repository<List<Stop>, Any>,
                                        private val routesRepository: Repository<List<Route>, Any>) {

    fun tempFunction1(): Observable<List<Stop>> {
        return stopsRepository.get(0)
    }

    fun tempFunction2(): Observable<List<Route>> {
        return routesRepository.get(0)
    }

}
