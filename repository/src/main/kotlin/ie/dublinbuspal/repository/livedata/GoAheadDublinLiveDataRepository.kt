package ie.dublinbuspal.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.livedata.DublinBusGoAheadDublinLiveData
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable

class GoAheadDublinLiveDataRepository(
        private val store: Store<List<DublinBusGoAheadDublinLiveData>, String>
) : Repository<DublinBusGoAheadDublinLiveData> {

    override fun getAllById(id: String): Observable<List<DublinBusGoAheadDublinLiveData>> {
        return store.get(id).toObservable()
    }

    override fun getById(id: String): Observable<DublinBusGoAheadDublinLiveData> {
        throw UnsupportedOperationException()
    }

    override fun getAll(): Observable<List<DublinBusGoAheadDublinLiveData>> {
        throw UnsupportedOperationException()
    }

}
