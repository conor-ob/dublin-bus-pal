package ie.dublinbuspal.usecase.update

import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.model.stop.DefaultStop
import ie.dublinbuspal.model.stop.DublinBusGoAheadDublinStop
import ie.dublinbuspal.repository.Repository
import io.reactivex.Observable
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class UpdateStopsAndRoutesUseCase @Inject constructor(
        private val defaultStopRepository: Repository<DefaultStop>,
        private val defaultRouteRepository: Repository<DefaultRoute>,
        @Named("bac") private val dublinBusStopRepository: Repository<DublinBusGoAheadDublinStop>,
        @Named("gad") private val goAheadDublinStopRepository: Repository<DublinBusGoAheadDublinStop>,
        private val goAheadDublinRouteRepository: Repository<GoAheadDublinRoute>
) {

    fun update(): Observable<Int> {
        return Observable.combineLatest(
                defaultStopRepository.refresh().startWith(false).subscribeOn(Schedulers.io()),
                defaultRouteRepository.refresh().startWith(false).subscribeOn(Schedulers.io()),
                dublinBusStopRepository.refresh().startWith(false).subscribeOn(Schedulers.io()),
                goAheadDublinStopRepository.refresh().startWith(false).subscribeOn(Schedulers.io()),
                goAheadDublinRouteRepository.refresh().startWith(false).subscribeOn(Schedulers.io()),
                Function5 { r1, r2, r3, r4, r5 -> update(r1, r2, r3, r4, r5) }
        )
    }

    private fun update(r1: Boolean, r2: Boolean, r3: Boolean, r4: Boolean, r5: Boolean): Int {
        val increment = (100 / 5)
        var total = 0
        if (r1) total += increment
        if (r2) total += increment
        if (r3) total += increment
        if (r4) total += increment
        if (r5) total += increment
        return total
    }

}
