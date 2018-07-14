package ie.dublinbuspal.domain.repository.route

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.route.Route
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import io.reactivex.Observable

class RouteRepository(private val store: StoreRoom<List<Route>, RoutesRequestXml>) : Repository<List<Route>, RoutesRequestXml> {

    override fun get(key: RoutesRequestXml): Observable<List<Route>> {
        return store.get(key)
    }

    override fun fetch(key: RoutesRequestXml): Observable<List<Route>> {
        return store.fetch(key)
    }

}