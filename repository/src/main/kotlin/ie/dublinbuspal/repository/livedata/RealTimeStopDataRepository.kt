package ie.dublinbuspal.repository.livedata

import com.nytimes.android.external.store3.base.impl.Store
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.model.livedata.LiveDataRequestBodyXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestRootXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import io.reactivex.Observable

class RealTimeStopDataRepository(private val store: Store<List<RealTimeStopData>, LiveDataRequestXml>) : Repository<RealTimeStopData> {

    override fun getById(id: String): Observable<RealTimeStopData> {
        throw UnsupportedOperationException()
    }

    override fun getAll(): Observable<List<RealTimeStopData>> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<RealTimeStopData>> {
        return store.get(buildLiveDataKey(id)).toObservable()
    }

    private fun buildLiveDataKey(stopId: String): LiveDataRequestXml {
        val root = LiveDataRequestRootXml(stopId, true.toString().toLowerCase())
        val body = LiveDataRequestBodyXml(root)
        return LiveDataRequestXml(body)
    }

}
