package ie.dublinbuspal.android.view.nearby

import android.util.Log
import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.base.Coordinate
import ie.dublinbuspal.domain.usecase.NearbyBusStopsUseCase
import javax.inject.Inject

class NearbyPresenter @Inject constructor(private val useCase: NearbyBusStopsUseCase) : BasePresenter<NearbyView>() {

    fun start(coordinate: Coordinate) {
        useCase.getNearbyBusStops(coordinate)
                .compose(applySchedulers())
                .doOnNext { ifViewAttached { view -> view.showBusStops(it) } }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()
    }

}
