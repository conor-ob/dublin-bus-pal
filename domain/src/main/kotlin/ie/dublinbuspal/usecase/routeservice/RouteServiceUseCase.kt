package ie.dublinbuspal.usecase.routeservice

import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.KeyedRepository
import ie.dublinbuspal.util.Operator
import io.reactivex.Observable
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(
        private val routeServiceRepository: KeyedRepository<Pair<String, Operator>, RouteService>
) {

    fun getRouteService(routeId: String, operator: Operator): Observable<RouteService> {
        return routeServiceRepository.getById(Pair(routeId, operator))
    }

}
