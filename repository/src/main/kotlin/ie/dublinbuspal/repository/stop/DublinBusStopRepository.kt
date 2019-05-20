package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class DublinBusStopRepository(
        private val store: StoreRoom<List<DublinBusStop>, String>
) : Repository<DublinBusStop> {

    private val key = "stops"

    override fun getAll(): Observable<List<DublinBusStop>> {
        return store.get(key)
    }

    override fun refresh(): Observable<Boolean> {
        return store.fetch(key).map { true }
    }

    override fun getById(id: String): Observable<DublinBusStop> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<DublinBusStop>> {
        throw UnsupportedOperationException()
    }

}
