package ie.dublinbuspal.usecase.update

import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateStopsAndRoutesUseCase @Inject constructor(
        private val dublinBusStopRepository: Repository<DublinBusStop>,
        private val goAheadDublinRouteRepository: Repository<GoAheadDublinRoute>
) {

    fun update(): Observable<Int> {
        return Observable.combineLatest(
                dublinBusStopRepository.refresh().startWith(false).subscribeOn(Schedulers.io()),
                goAheadDublinRouteRepository.refresh().startWith(false).subscribeOn(Schedulers.io()),
                BiFunction { r1, r2 -> update(r1, r2) }
        )
    }

    private fun update(r1: Boolean, r2: Boolean): Int {
        val increment = (100 / 2)
        var total = 0
        if (r1) total += increment
        if (r2) total += increment
        return total
    }

}
