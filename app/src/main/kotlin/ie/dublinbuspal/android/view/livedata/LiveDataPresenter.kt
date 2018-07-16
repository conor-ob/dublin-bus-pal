package ie.dublinbuspal.android.view.livedata

import android.util.Log
import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.domain.usecase.livedata.LiveDataUseCase
import javax.inject.Inject

class LiveDataPresenter @Inject constructor(private val useCase: LiveDataUseCase): BasePresenter<LiveDataView>() {

    fun start(stopId: String) {
        useCase.getLiveData(stopId)
                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showLiveData(it) } }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()

        useCase.getStopService(stopId)
                .compose(applyObservableSchedulers())
                .doOnNext { Log.i(javaClass.simpleName, it.toString()) }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()
    }

}
