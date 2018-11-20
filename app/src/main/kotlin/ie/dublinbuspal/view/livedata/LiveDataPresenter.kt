package ie.dublinbuspal.view.livedata

import android.util.Log
import ie.dublinbuspal.usecase.livedata.LiveDataUseCase
import ie.dublinbuspal.view.BasePresenter
import javax.inject.Inject

class LiveDataPresenter @Inject constructor(private val useCase: LiveDataUseCase): BasePresenter<LiveDataView>() {

    fun start(stopId: String) {
        getBusStop(stopId)

//        useCase.getStopService(stopId)
//                .compose(applyObservableSchedulers())
//                .doOnNext { Log.i(javaClass.simpleName, it.toString()) }
//                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
//                .subscribe()
    }

    private fun getBusStop(stopId: String) {
        useCase.getBusStop(stopId)
                .filter { it.id() != "-1" }
                .doOnNext {
                    ifViewAttached { view -> view.showBusStop(it) }
                    getLiveData(it.id())
                }
                .subscribe()
    }

    private fun getLiveData(stopId: String) {
        useCase.getLiveData(stopId)
                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showLiveData(it) } }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()
    }

}
