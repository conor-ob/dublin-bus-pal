package ie.dublinbuspal.repository.routeservice

import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class DefaultRouteServiceRepository(
        private val store: Store<RouteService, String>
) : Repository<RouteService> {

    override fun getById(id: String): Observable<RouteService> {
        return store.get(id).toObservable()
    }

    override fun getAll(): Observable<List<RouteService>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<RouteService>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
