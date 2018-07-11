package ie.dublinbuspal.domain.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import io.reactivex.Observable

class LiveDataRepository(private val store: Store<List<LiveData>, LiveDataRequestXml>) : Repository<List<LiveData>, LiveDataRequestXml> {

    override fun get(key: LiveDataRequestXml): Observable<List<LiveData>> {
        return store.get(key).toObservable()
    }

    override fun fetch(key: LiveDataRequestXml): Observable<List<LiveData>> {
        return store.fetch(key).toObservable()
    }

}
