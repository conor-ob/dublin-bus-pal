package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class DefaultRouteRepository(private val store: StoreRoom<List<DefaultRoute>, String>) : Repository<DefaultRoute> {

    private val key = javaClass.simpleName

    override fun getAll(): Observable<List<DefaultRoute>> {
        return store.get(key)
    }

    override fun refresh(): Observable<Boolean> {
        return store.get(key).map { true }
    }

    override fun getById(id: String): Observable<DefaultRoute> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<DefaultRoute>> {
        throw UnsupportedOperationException()
    }

}