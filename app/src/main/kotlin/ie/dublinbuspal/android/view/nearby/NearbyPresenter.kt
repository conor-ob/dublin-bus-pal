package ie.dublinbuspal.android.view.nearby

import ie.dublinbuspal.Coordinate
import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.usecase.nearby.NearbyStopsUseCase
import timber.log.Timber
import javax.inject.Inject

class NearbyPresenter @Inject constructor(private val useCase: NearbyStopsUseCase) : BasePresenter<NearbyView>() {

    fun preload() {
        useCase.preload()
                .compose(applyCompletableSchedulers())
                .doOnComplete { Timber.d("done") }
                .doOnError { Timber.e(it) }
                .subscribe()
    }

    fun start() {
        useCase.getLastLocation()
                .compose(applyObservableSchedulers())
                .doOnNext {
                    ifViewAttached { view -> view.moveCamera(it) }
                    refresh(it.first)
                }
                .doOnError { Timber.e(it) }
                .subscribe()
    }

    fun refresh(coordinate: Coordinate) {
        useCase.getNearbyBusStops(coordinate)
                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showBusStops(it) } }
                .doOnError { Timber.e(it) }
                .subscribe()
    }

    fun stop(location: Pair<Coordinate, Float>) {
        useCase.saveLastLocation(location)
                .compose(applyCompletableSchedulers())
                .doOnComplete { Timber.d("done") }
                .doOnError { Timber.e(it) }
                .subscribe()
    }

}
