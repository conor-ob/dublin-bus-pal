package ie.dublinbuspal.domain.usecase.routeservice

import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.routeservice.RouteService
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestBodyXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestRootXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import io.reactivex.Observable
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(private val repository: Repository<RouteService, RouteServiceRequestXml>) {

    fun getRouteService(routeId: String): Observable<RouteService> {
        return repository.get(buildKey(routeId))
    }

    private fun buildKey(routeId: String): RouteServiceRequestXml {
        val root = RouteServiceRequestRootXml(routeId)
        val body = RouteServiceRequestBodyXml(root)
        return RouteServiceRequestXml(body)
    }

}
