package ie.dublinbuspal.view.news.rss

import ie.dublinbuspal.usecase.rss.RssNewsUseCase
import ie.dublinbuspal.view.BasePresenter
import javax.inject.Inject

class RssNewsPresenter @Inject constructor(private val useCase: RssNewsUseCase) : BasePresenter<RssNewsView>() {

    fun start() {
        useCase.getRssNews()
                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showRssNews(it) } }
//                .doOnError { Timber.e(it) }
                .subscribe()
    }

}
