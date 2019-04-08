package ie.dublinbuspal.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class LiveDataRepository(
        private val store: Store<List<LiveData>, String>
) : Repository<LiveData> {

    override fun getAllById(id: String): Observable<List<LiveData>> {
        return store.get(id)
                .map { liveData -> liveData.sortedBy { it.dueTime.minutes } }
                .toObservable()
    }

    override fun getById(id: String): Observable<LiveData> {
        throw UnsupportedOperationException()
    }

    override fun getAll(): Observable<List<LiveData>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
