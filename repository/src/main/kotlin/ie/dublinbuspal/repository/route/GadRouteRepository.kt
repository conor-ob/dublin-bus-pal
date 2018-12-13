package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class GadRouteRepository(
        private val store: Store<List<GoAheadDublinRoute>, String>
) : Repository<GoAheadDublinRoute> {

    private val key = javaClass.simpleName

    override fun getAll(): Observable<List<GoAheadDublinRoute>> {
        return store.get(key).toObservable()
    }

    override fun getById(id: String): Observable<GoAheadDublinRoute> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<GoAheadDublinRoute>> {
        throw UnsupportedOperationException()
    }

}
