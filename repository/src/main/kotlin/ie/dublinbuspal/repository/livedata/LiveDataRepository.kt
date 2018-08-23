package ie.dublinbuspal.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.model.livedata.LiveDataRequestBodyXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestRootXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import io.reactivex.Observable

class LiveDataRepository(private val store: Store<List<LiveData>, LiveDataRequestXml>) : Repository<List<LiveData>, String> {

    override fun get(key: String): Observable<List<LiveData>> {
        return store.get(buildLiveDataKey(key)).toObservable()
    }

    override fun fetch(key: String): Observable<List<LiveData>> {
        return store.fetch(buildLiveDataKey(key)).toObservable()
    }

    private fun buildLiveDataKey(stopId: String): LiveDataRequestXml {
        val root = LiveDataRequestRootXml(stopId, true.toString().toLowerCase())
        val body = LiveDataRequestBodyXml(root)
        return LiveDataRequestXml(body)
    }

}
