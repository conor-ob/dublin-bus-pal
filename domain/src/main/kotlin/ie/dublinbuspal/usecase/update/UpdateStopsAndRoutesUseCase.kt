package ie.dublinbuspal.usecase.update

import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.util.DownloadProgressListener
import ie.dublinbuspal.util.DownloadProgressObserver
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateStopsAndRoutesUseCase @Inject constructor(
        private val dublinBusStopRepository: Repository<DublinBusStop>,
        private val dublinBusRouteRepository: Repository<Route>,
        private val downloadProgressListener: DownloadProgressListener
) {

    fun update(): Observable<Boolean> {
        return Observable.combineLatest(
                dublinBusStopRepository.refresh().subscribeOn(Schedulers.newThread()),
                dublinBusRouteRepository.refresh().subscribeOn(Schedulers.newThread()),
                BiFunction { r1, r2 -> r1 && r2 }
        )
    }

    fun registerObserver(downloadProgressObserver: DownloadProgressObserver) {
        downloadProgressListener.registerObserver(downloadProgressObserver)
    }

    fun unregisterObserver() {
        downloadProgressListener.unregisterObserver()
    }

}
