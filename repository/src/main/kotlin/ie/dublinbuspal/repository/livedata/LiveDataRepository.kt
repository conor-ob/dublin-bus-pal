package ie.dublinbuspal.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.model.livedata.LiveDataRequestBodyXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestRootXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import io.reactivex.Observable

class LiveDataRepository(private val store: Store<List<LiveData>, LiveDataRequestXml>) : Repository<LiveData> {

    override fun getById(id: String): Observable<LiveData> {
        throw UnsupportedOperationException()
    }

    override fun getAll(): Observable<List<LiveData>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<LiveData>> {
        return store.fetch(buildLiveDataKey(id)).toObservable()
    }

    private fun buildLiveDataKey(stopId: String): LiveDataRequestXml {
        val root = LiveDataRequestRootXml(stopId, true.toString().toLowerCase())
        val body = LiveDataRequestBodyXml(root)
        return LiveDataRequestXml(body)
    }

}
