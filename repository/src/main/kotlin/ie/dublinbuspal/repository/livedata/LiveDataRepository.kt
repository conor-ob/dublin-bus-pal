package ie.dublinbuspal.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.domain.repository.Repository
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import io.reactivex.Observable

class LiveDataRepository(private val store: Store<List<LiveData>, LiveDataRequestXml>) : Repository<List<LiveData>, LiveDataRequestXml> {

    override fun get(key: LiveDataRequestXml): Observable<List<LiveData>> {
        return store.get(key).toObservable()
//        return Observable.just(Arrays.asList(LiveData("46A", "Blackrock", DueTime(6L, "22:23"))))
    }

    override fun fetch(key: LiveDataRequestXml): Observable<List<LiveData>> {
        return store.fetch(key).toObservable()
    }

}
