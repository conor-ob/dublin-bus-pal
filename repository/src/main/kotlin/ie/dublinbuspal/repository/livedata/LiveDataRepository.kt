package ie.dublinbuspal.repository.livedata

import ie.dublinbuspal.model.livedata.DublinBusGoAheadDublinLiveData
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class LiveDataRepository(
        private val realTimeStopDataRepository: Repository<RealTimeStopData>,
        private val realTimeBusInformationRepository: Repository<DublinBusGoAheadDublinLiveData>,
        private val realTimeStopDataMapper: Mapper<RealTimeStopData, LiveData>,
        private val realTimeBusInformationMapper: Mapper<DublinBusGoAheadDublinLiveData, LiveData>
) : Repository<LiveData> {

    override fun getAllById(id: String): Observable<List<LiveData>> {
        return Observable.combineLatest(
                realTimeStopDataRepository.getAllById(id).subscribeOn(Schedulers.io()),
                realTimeBusInformationRepository.getAllById(id).subscribeOn(Schedulers.io()),
                BiFunction { r1, r2 -> resolveAndSort(r1, r2) }
        )
    }

    private fun resolveAndSort(realTimeStopData: List<RealTimeStopData>, realTimeBusInformation: List<DublinBusGoAheadDublinLiveData>): List<LiveData> {
        val liveData = mutableListOf<LiveData>()
        liveData.addAll(realTimeStopDataMapper.map(realTimeStopData))
        liveData.addAll(realTimeBusInformationMapper.map(realTimeBusInformation))
        return liveData.sortedBy { it.dueTime.minutes }
    }

    override fun getById(id: String): Observable<LiveData> {
        throw UnsupportedOperationException()
    }

    override fun getAll(): Observable<List<LiveData>> {
        throw UnsupportedOperationException()
    }

    override fun refresh(): Observable<Boolean> {
        throw UnsupportedOperationException()
    }

}
