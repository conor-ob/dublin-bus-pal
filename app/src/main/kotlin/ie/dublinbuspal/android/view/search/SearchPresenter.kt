package ie.dublinbuspal.android.view.search

import android.util.Log
import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.domain.usecase.search.SearchUseCase
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val useCase: SearchUseCase) : BasePresenter<SearchView>() {

    fun start() {
        useCase.tempFunction1()
                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showStops(it) } }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()

        useCase.tempFunction2()
                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showRoutes(it) } }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()

    }

//    fun function() {
//        Observable.zip<List<Stop>, List<Route>, Bundle>(useCase.tempFunction1(), useCase.tempFunction2(), { stops, routes ->
//
//        }).subscribe()
//    }

}