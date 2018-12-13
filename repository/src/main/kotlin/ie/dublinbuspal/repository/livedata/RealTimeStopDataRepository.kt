package ie.dublinbuspal.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class RealTimeStopDataRepository(private val store: Store<List<RealTimeStopData>, String>) : Repository<RealTimeStopData> {

    override fun getById(id: String): Observable<RealTimeStopData> {
        throw UnsupportedOperationException()
    }

    override fun getAll(): Observable<List<RealTimeStopData>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<RealTimeStopData>> {
        return store.get(id).toObservable()
    }

}
