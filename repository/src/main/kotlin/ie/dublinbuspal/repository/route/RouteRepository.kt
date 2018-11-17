package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.model.route.RoutesRequestBodyXml
import ie.dublinbuspal.service.model.route.RoutesRequestRootXml
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import io.reactivex.Observable

class RouteRepository(private val store: StoreRoom<List<Route>, RoutesRequestXml>) : Repository<Route> {

    override fun getAll(): Observable<List<Route>> {
        return store.get(this.key)
    }

    override fun getById(id: String): Observable<Route> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<Route>> {
        throw UnsupportedOperationException()
    }

    val key : RoutesRequestXml by lazy {
        val root = RoutesRequestRootXml("")
        val body = RoutesRequestBodyXml(root)
        return@lazy RoutesRequestXml(body)
    }

}