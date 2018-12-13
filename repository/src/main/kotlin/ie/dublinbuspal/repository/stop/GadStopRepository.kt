package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stop.SmartDublinStop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class GadStopRepository(
        private val store: StoreRoom<List<SmartDublinStop>, String>
) : Repository<SmartDublinStop> {

    private val key = javaClass.simpleName

    override fun getById(id: String): Observable<SmartDublinStop> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<SmartDublinStop>> {
        throw UnsupportedOperationException()
    }

    override fun getAll(): Observable<List<SmartDublinStop>> {
        return store.get(key)
    }

}
