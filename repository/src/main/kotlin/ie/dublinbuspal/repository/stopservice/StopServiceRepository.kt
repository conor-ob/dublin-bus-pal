package ie.dublinbuspal.repository.stopservice

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stopservice.StopService
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class StopServiceRepository(private val store: StoreRoom<StopService, String>) : Repository<StopService> {

    override fun getAll(): Observable<List<StopService>> {
        throw UnsupportedOperationException()
    }

    override fun getById(id: String): Observable<StopService> {
        return store.get(id)
    }

    override fun getAllById(id: String): Observable<List<StopService>> {
        throw UnsupportedOperationException()
    }

}
