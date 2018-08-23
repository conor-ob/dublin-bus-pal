package ie.dublinbuspal.android.view.routeservice

import android.util.Log
import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.usecase.routeservice.RouteServiceUseCase
import javax.inject.Inject

class RouteServicePresenter @Inject constructor(private val useCase: RouteServiceUseCase) : BasePresenter<RouteServiceView>() {

    fun start(routeId: String) {
        useCase.getRouteService(routeId)
                .compose(applyObservableSchedulers())
                .doOnNext { Log.i(javaClass.simpleName, it.toString()) }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()
    }

}
