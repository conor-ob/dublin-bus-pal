package ie.dublinbuspal.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.livedata.RealTimeBusInformation
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class RealTimeBusInformationRepository(private val store: Store<List<RealTimeBusInformation>, String>) : Repository<RealTimeBusInformation> {

    override fun getAllById(id: String): Observable<List<RealTimeBusInformation>> {
        return store.get(id).toObservable()
    }

    override fun getById(id: String): Observable<RealTimeBusInformation> {
        throw UnsupportedOperationException()
    }

    override fun getAll(): Observable<List<RealTimeBusInformation>> {
        throw UnsupportedOperationException()
    }

}
