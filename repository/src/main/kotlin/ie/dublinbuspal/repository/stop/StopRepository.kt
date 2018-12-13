package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class StopRepository(private val store: StoreRoom<List<Stop>, String>): Repository<Stop> {

    private val key = javaClass.simpleName

    override fun getAll(): Observable<List<Stop>> {
        return store.get(key)
    }

    override fun getById(id: String): Observable<Stop> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<Stop>> {
        throw UnsupportedOperationException()
    }

}