package ie.dublinbuspal.repository.routeservice

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestBodyXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestRootXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import io.reactivex.Observable

class RouteServiceRepository(private val store: StoreRoom<RouteService, RouteServiceRequestXml>) : Repository<RouteService> {

    override fun getAll(): Observable<List<RouteService>> {
        throw UnsupportedOperationException()
    }

    override fun getById(id: String): Observable<RouteService> {
        return store.get(buildKey(id))
    }

    override fun getAllById(id: String): Observable<List<RouteService>> {
        throw UnsupportedOperationException()
    }

    private fun buildKey(routeId: String): RouteServiceRequestXml {
        val root = RouteServiceRequestRootXml(routeId)
        val body = RouteServiceRequestBodyXml(root)
        return RouteServiceRequestXml(body)
    }

}
