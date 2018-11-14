package ie.dublinbuspal.usecase.search

import ie.dublinbuspal.model.stop.ResolvedStop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val stopsRepository: Repository<List<ResolvedStop>, Any>) {

    fun getAllStops(): Observable<List<ResolvedStop>> {
        return stopsRepository.get(0)
    }

//    fun tempFunction2(): Observable<List<Route>> {
//        return routesRepository.get(0)
//    }

}
