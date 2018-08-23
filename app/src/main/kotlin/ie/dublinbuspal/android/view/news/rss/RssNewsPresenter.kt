package ie.dublinbuspal.android.view.news.rss

import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.usecase.rss.RssNewsUseCase
import timber.log.Timber
import javax.inject.Inject

class RssNewsPresenter @Inject constructor(private val useCase: RssNewsUseCase) : BasePresenter<RssNewsView>() {

    fun start() {
        useCase.getRssNews()
                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showRssNews(it) } }
                .doOnError { Timber.e(it) }
                .subscribe()
    }

}
