package ie.dublinbuspal.domain.usecase.routeservice

import ie.dublinbuspal.domain.model.routeservice.RouteService
import ie.dublinbuspal.domain.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(private val repository: Repository<RouteService, String>) {

    fun getRouteService(routeId: String): Observable<RouteService> {
        return repository.get(routeId)
    }

}
