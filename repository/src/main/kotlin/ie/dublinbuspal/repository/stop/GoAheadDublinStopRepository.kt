package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stop.DublinBusGoAheadDublinStop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class GoAheadDublinStopRepository(
        private val store: StoreRoom<List<DublinBusGoAheadDublinStop>, String>
) : Repository<DublinBusGoAheadDublinStop> {

    private val key = "go_ahead_dublin_stops"

    override fun getAll(): Observable<List<DublinBusGoAheadDublinStop>> {
        return store.get(key)
    }

    override fun refresh(): Observable<Boolean> {
        return store.fetch(key).map { true }
    }

    override fun getAllById(id: String): Observable<List<DublinBusGoAheadDublinStop>> {
        throw UnsupportedOperationException()
    }

    override fun getById(id: String): Observable<DublinBusGoAheadDublinStop> {
        throw UnsupportedOperationException()
    }

}
