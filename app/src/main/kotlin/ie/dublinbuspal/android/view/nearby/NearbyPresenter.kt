package ie.dublinbuspal.android.view.nearby

import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.domain.usecase.nearby.NearbyStopsUseCase
import timber.log.Timber
import javax.inject.Inject

class NearbyPresenter @Inject constructor(private val useCase: NearbyStopsUseCase) : BasePresenter<NearbyView>() {

    fun start() {
        useCase.getLastKnownLocation()
                .compose(applyObservableSchedulers())
                .doOnNext {
                    ifViewAttached { view -> view.moveCamera(it) }
                    refresh(it)
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

    fun stop(coordinate: Coordinate) {
        useCase.saveLocation(coordinate)
                .compose(applyCompletableSchedulers())
                .doOnComplete { Timber.d("done") }
                .doOnError { Timber.e(it) }
                .subscribe()
    }

}
