package ie.dublinbuspal.domain.usecase.livedata

import ie.dublinbuspal.domain.model.livedata.LiveData
import ie.dublinbuspal.domain.model.stopservice.StopService
import ie.dublinbuspal.domain.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class LiveDataUseCase @Inject constructor(private val liveDataRepository: Repository<List<LiveData>, String>,
                                          private val stopServiceRepository: Repository<StopService, String>) {

    fun getLiveData(stopId: String): Observable<List<LiveData>> {
        return liveDataRepository.get(stopId)
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

    fun getStopService(stopId: String): Observable<StopService> {
        return stopServiceRepository.get(stopId)
    }

}
