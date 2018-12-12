package ie.dublinbuspal.usecase.routeservice

import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(private val repository: Repository<RouteService>) {

    fun getRouteService(routeId: String): Observable<RouteService> {
        return repository.getById(routeId)
    }

}
