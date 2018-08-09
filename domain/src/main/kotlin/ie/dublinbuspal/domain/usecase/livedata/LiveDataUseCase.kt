package ie.dublinbuspal.domain.usecase.livedata

import ie.dublinbuspal.domain.repository.Repository
import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.domain.model.stopservice.StopService
import ie.dublinbuspal.service.model.livedata.LiveDataRequestBodyXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestRootXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestBodyXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestRootXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import io.reactivex.Observable
import javax.inject.Inject

class LiveDataUseCase @Inject constructor(private val liveDataRepository: Repository<List<LiveData>, LiveDataRequestXml>,
                                          private val stopServiceRepository: Repository<StopService, StopServiceRequestXml>) {

    fun getLiveData(stopId: String): Observable<List<LiveData>> {
        return liveDataRepository.get(buildLiveDataKey(stopId))
    }

    fun getCondensedLiveData(stopId: String): Observable<Map<Pair<String, String>, List<LiveData>>> {
        return getLiveData(stopId).map { condenseLiveData(it) }
    }

    private fun condenseLiveData(liveData: List<LiveData>): Map<Pair<String, String>, List<LiveData>>? {
        val condensedLivedata = LinkedHashMap<Pair<String, String>, MutableList<LiveData>>()
        for (data in liveData) {
            val key = Pair(data.routeId, data.destination)
            var liveDataForRoute = condensedLivedata[key]
            if (liveDataForRoute == null) {
                liveDataForRoute = mutableListOf()
                condensedLivedata[key] = liveDataForRoute
            }
            liveDataForRoute.add(data)
        }
        return condensedLivedata
    }

    private fun buildLiveDataKey(stopId: String): LiveDataRequestXml {
        val root = LiveDataRequestRootXml(stopId, true.toString().toLowerCase())
        val body = LiveDataRequestBodyXml(root)
        return LiveDataRequestXml(body)
    }

    fun getStopService(stopId: String): Observable<StopService> {
        return stopServiceRepository.get(buildStopServiceKey(stopId))
    }

    private fun buildStopServiceKey(stopId: String): StopServiceRequestXml {
        val root = StopServiceRequestRootXml(stopId)
        val body = StopServiceRequestBodyXml(root)
        return StopServiceRequestXml(body)
    }

}
