package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class DublinBusRouteRepository(
        private val store: StoreRoom<List<Route>, String>
) : Repository<Route> {

    private val key = "routes"

    override fun getAll(): Observable<List<Route>> {
        return store.get(key)
    }

    override fun refresh(): Observable<Boolean> {
        return store.fetch(key).map { true }
    }

    override fun getById(id: String): Observable<Route> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<Route>> {
        throw UnsupportedOperationException()
    }

}