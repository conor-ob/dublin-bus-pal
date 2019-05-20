package ie.dublinbuspal.usecase.routeservice

import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.KeyedRepository
import io.reactivex.Observable
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(
        private val routeServiceRepository: KeyedRepository<Pair<String, String>, RouteService>
) {

    fun getRouteService(routeId: String, operatorId: String): Observable<RouteService> {
        return routeServiceRepository.getById(Pair(routeId, operatorId))
    }

}
