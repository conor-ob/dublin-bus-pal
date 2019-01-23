package ie.dublinbuspal.usecase.livedata

import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class LiveDataUseCase @Inject constructor(
        private val liveDataRepository: Repository<LiveData>,
        private val stopRepository: Repository<Stop>
) {

    fun getBusStop(stopId: String): Observable<Stop> {
        return stopRepository.getById(stopId)
    }

    fun getLiveData(stopId: String): Observable<List<LiveData>> {
        return liveDataRepository.getAllById(stopId)
    }

    fun getLiveData(stopId: String, routeFilter: List<String>): Observable<List<LiveData>> {
        return liveDataRepository.getAllById(stopId).map { liveData -> liveData.filter { routeFilter.contains(it.routeId) } }
    }

    fun getCondensedLiveData(stopId: String): Observable<Map<Pair<String, Destination>, List<LiveData>>> {
        return getLiveData(stopId).map { condenseLiveData(it) }
    }

    private fun condenseLiveData(liveData: List<LiveData>): Map<Pair<String, Destination>, List<LiveData>> {
        val condensedLivedata = LinkedHashMap<Pair<String, Destination>, MutableList<LiveData>>()
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

}
