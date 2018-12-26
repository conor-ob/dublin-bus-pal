package ie.dublinbuspal.repository.routeservice

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.routeservice.GoAheadDublinRouteService
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class GoAheadDublinRouteServiceRepository(
        private val store: Store<GoAheadDublinRouteService, String>
) : Repository<GoAheadDublinRouteService> {

    override fun getById(id: String): Observable<GoAheadDublinRouteService> {
        return store.get(id).toObservable()
    }

    override fun getAll(): Observable<List<GoAheadDublinRouteService>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<GoAheadDublinRouteService>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
