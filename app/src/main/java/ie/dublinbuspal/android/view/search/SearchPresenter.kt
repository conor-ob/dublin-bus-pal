package ie.dublinbuspal.android.view.search

import android.util.Log
import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.domain.usecase.search.SearchUseCase
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val useCase: SearchUseCase) : BasePresenter<SearchView>() {

    fun start() {
        useCase.tempFunction1()
                .compose(applySchedulers())
                .doOnNext { Log.i(javaClass.simpleName, it.toString()) }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()

        useCase.tempFunction2()
                .compose(applySchedulers())
                .doOnNext { Log.i(javaClass.simpleName, it.toString()) }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()
    }

}