package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stop.DefaultStop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class DefaultStopRepository(private val store: StoreRoom<List<DefaultStop>, String>): Repository<DefaultStop> {

    private val key = "default_stops"

    override fun getAll(): Observable<List<DefaultStop>> {
        return store.get(key)
    }

    override fun refresh(): Observable<Boolean> {
        return store.fetch(key).map { true }
    }

    override fun getById(id: String): Observable<DefaultStop> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<DefaultStop>> {
        throw UnsupportedOperationException()
    }

}