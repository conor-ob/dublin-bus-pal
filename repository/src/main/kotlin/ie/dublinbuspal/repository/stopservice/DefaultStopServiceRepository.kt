package ie.dublinbuspal.repository.stopservice

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stopservice.DefaultStopService
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class DefaultStopServiceRepository(private val store: StoreRoom<DefaultStopService, String>) : Repository<DefaultStopService> {

    override fun getById(id: String): Observable<DefaultStopService> {
        return store.get(id)
    }

    override fun getAll(): Observable<List<DefaultStopService>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<DefaultStopService>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
