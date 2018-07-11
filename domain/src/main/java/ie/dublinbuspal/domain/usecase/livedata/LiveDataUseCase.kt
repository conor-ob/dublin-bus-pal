package ie.dublinbuspal.domain.usecase.livedata

import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.service.model.livedata.LiveDataRequestBodyXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestRootXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import io.reactivex.Observable
import javax.inject.Inject

class LiveDataUseCase @Inject constructor(private val repository: Repository<List<LiveData>, LiveDataRequestXml>) {

    fun getLiveData(stopId: String): Observable<List<LiveData>> {
        return repository.get(buildKey(stopId))
    }

    private fun buildKey(stopId: String): LiveDataRequestXml {
        val root = LiveDataRequestRootXml(stopId, true.toString().toLowerCase())
        val body = LiveDataRequestBodyXml(root)
        return LiveDataRequestXml(body)
    }

}
