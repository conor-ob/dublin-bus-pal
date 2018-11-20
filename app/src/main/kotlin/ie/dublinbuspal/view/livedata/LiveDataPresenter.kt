package ie.dublinbuspal.view.livedata

import ie.dublinbuspal.usecase.livedata.LiveDataUseCase
import ie.dublinbuspal.view.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class LiveDataPresenter @Inject constructor(private val useCase: LiveDataUseCase) : BasePresenter<LiveDataView>() {

    private val subscriptions = CompositeDisposable()

    fun start(stopId: String) {
        getBusStop(stopId)

//        useCase.getStopService(stopId)
//                .compose(applyObservableSchedulers())
//                .doOnNext { Log.i(javaClass.simpleName, it.toString()) }
//                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
//                .subscribe()
    }

    private fun getBusStop(stopId: String) {
        subscriptions.add(useCase.getBusStop(stopId)
                .filter { it.id() != "-1" }
                .distinctUntilChanged { s1, s2 -> s1.id() == s2.id() }
                .doOnNext {
                    Timber.d(it.toString())
                    ifViewAttached { view -> view.showBusStop(it) }
                    getLiveData(it.id())
                }
                .subscribe())
    }

    private fun getLiveData(stopId: String) {
        subscriptions.add(useCase.getLiveData(stopId)
                .compose(applyObservableSchedulers())
                .doOnNext {
                    Timber.d(it.toString())
                    ifViewAttached { view -> view.showLiveData(it) }
                }
                .doOnError { Timber.e(it) }
                .subscribe())
    }

    fun stop() {
        //TODO check how to do this properly
        subscriptions.clear()
        if (!subscriptions.isDisposed) {
            subscriptions.dispose()
        }
    }

}
