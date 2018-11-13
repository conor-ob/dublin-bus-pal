package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.model.route.RoutesRequestBodyXml
import ie.dublinbuspal.service.model.route.RoutesRequestRootXml
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import io.reactivex.Observable

class RouteRepository(private val store: StoreRoom<List<Route>, RoutesRequestXml>) : Repository<List<Route>, Any> {

    override fun get(key: Any): Observable<List<Route>> {
        return store.get(this.key)
    }

    override fun fetch(key: Any): Observable<List<Route>> {
        return store.fetch(this.key)
    }

    val key : RoutesRequestXml by lazy {
        val root = RoutesRequestRootXml("")
        val body = RoutesRequestBodyXml(root)
        return@lazy RoutesRequestXml(body)
    }

}