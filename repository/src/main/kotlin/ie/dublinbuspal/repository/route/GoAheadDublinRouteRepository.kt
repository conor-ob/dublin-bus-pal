package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class GoAheadDublinRouteRepository(
        private val store: StoreRoom<List<GoAheadDublinRoute>, String>
) : Repository<GoAheadDublinRoute> {

    private val key = "go_ahead_dublin_routes"

    override fun getAll(): Observable<List<GoAheadDublinRoute>> {
        return store.get(key)
    }

    override fun refresh(): Observable<Boolean> {
        return store.fetch(key).map { true }
    }

    override fun getById(id: String): Observable<GoAheadDublinRoute> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<GoAheadDublinRoute>> {
        throw UnsupportedOperationException()
    }

}
