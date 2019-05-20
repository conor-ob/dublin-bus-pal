package ie.dublinbuspal.repository.routeservice

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.repository.KeyedRepository
import ie.dublinbuspal.service.api.RtpiRouteService
import ie.dublinbuspal.util.Operator
import io.reactivex.Observable

class DublinBusRouteServiceRepository(
        private val store: Store<RtpiRouteService, Pair<String, String>>
) : KeyedRepository<Pair<String, String>, RtpiRouteService> {

    override fun getById(key: Pair<String, String>): Observable<RtpiRouteService> {
        return store.get(key).toObservable()
    }

    override fun getAll(): Observable<List<RtpiRouteService>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(key: Pair<String, String>): Observable<List<RtpiRouteService>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
