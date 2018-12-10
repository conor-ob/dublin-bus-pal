package ie.dublinbuspal.view.search

import ie.dublinbuspal.usecase.search.SearchUseCase
import ie.dublinbuspal.util.StringUtils
import ie.dublinbuspal.view.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val useCase: SearchUseCase) : BasePresenter<SearchView>() {

    fun start() {
        search(StringUtils.EMPTY_STRING)
    }

    fun search(value: String?) {
        useCase.search(value)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { ifViewAttached { view -> view.showSearchResult(it) } }
                .subscribe()
    }

}
