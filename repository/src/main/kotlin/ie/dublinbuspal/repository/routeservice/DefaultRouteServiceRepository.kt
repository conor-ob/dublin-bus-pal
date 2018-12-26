package ie.dublinbuspal.repository.routeservice

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.routeservice.DefaultRouteService
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class DefaultRouteServiceRepository(
        private val store: Store<DefaultRouteService, String>
) : Repository<DefaultRouteService> {

    override fun getById(id: String): Observable<DefaultRouteService> {
        return store.get(id).toObservable()
    }

    override fun getAll(): Observable<List<DefaultRouteService>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<DefaultRouteService>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
