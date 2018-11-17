package ie.dublinbuspal.view.nearby

import ie.dublinbuspal.usecase.nearby.NearbyStopsUseCase
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.view.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class NearbyPresenter @Inject constructor(private val useCase: NearbyStopsUseCase) : BasePresenter<NearbyView>() {

    fun start() {
        useCase.getLastLocation()
                .compose(applyObservableSchedulers())
                .doOnNext {
                    ifViewAttached { view -> view.moveCamera(it) }
                    refresh(it.first)
                }
//                .doOnError { Timber.e(it) }
                .subscribe()
    }

    fun refresh(coordinate: Coordinate) {
        useCase.getNearbyBusStops(coordinate)
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showBusStops(it) } }
//                .doOnError { Timber.e(it) }
                .subscribe()
    }

    fun stop(location: Pair<Coordinate, Float>) {
        useCase.saveLastLocation(location)
                .compose(applyCompletableSchedulers())
//                .doOnComplete { Timber.d("done") }
//                .doOnError { Timber.e(it) }
                .subscribe()
    }

}
