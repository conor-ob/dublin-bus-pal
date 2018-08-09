package ie.dublinbuspal.repository.routeservice

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.domain.model.routeservice.RouteService
import ie.dublinbuspal.domain.repository.Repository
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestBodyXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestRootXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import io.reactivex.Observable

class RouteServiceRepository(private val store: StoreRoom<RouteService, RouteServiceRequestXml>) : Repository<RouteService, String> {

    override fun get(key: String): Observable<RouteService> {
        return store.get(buildKey(key))
    }

    override fun fetch(key: String): Observable<RouteService> {
        return store.fetch(buildKey(key))
    }

    private fun buildKey(routeId: String): RouteServiceRequestXml {
        val root = RouteServiceRequestRootXml(routeId)
        val body = RouteServiceRequestBodyXml(root)
        return RouteServiceRequestXml(body)
    }

}
