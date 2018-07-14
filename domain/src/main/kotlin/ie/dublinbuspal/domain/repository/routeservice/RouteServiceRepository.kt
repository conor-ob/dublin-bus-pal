package ie.dublinbuspal.domain.repository.routeservice

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.routeservice.RouteService
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import io.reactivex.Observable

class RouteServiceRepository(private val store: StoreRoom<RouteService, RouteServiceRequestXml>) : Repository<RouteService, RouteServiceRequestXml> {

    override fun get(key: RouteServiceRequestXml): Observable<RouteService> {
        return store.get(key)
    }

    override fun fetch(key: RouteServiceRequestXml): Observable<RouteService> {
        return store.fetch(key)
    }

}
