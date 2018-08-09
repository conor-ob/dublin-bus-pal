package ie.dublinbuspal.domain.usecase.search

import ie.dublinbuspal.domain.model.route.Route
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.domain.repository.Repository
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
