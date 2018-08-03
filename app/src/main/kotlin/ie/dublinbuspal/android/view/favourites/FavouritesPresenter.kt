package ie.dublinbuspal.android.view.favourites

import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.domain.usecase.favourites.FavouritesUseCase
import ie.dublinbuspal.domain.usecase.livedata.LiveDataUseCase
import timber.log.Timber
import javax.inject.Inject

class FavouritesPresenter @Inject constructor(private val favouritesUseCase: FavouritesUseCase,
                                              private val liveDataUseCase: LiveDataUseCase) : BasePresenter<FavouritesView>() {

    fun start() {
        favouritesUseCase.getFavourites()
                .compose(applyObservableSchedulers())
                .doOnNext {
                    ifViewAttached { view -> view.showFavourites(it) }
                    getLiveData(it)
                }
                .doOnError { Timber.e(it) }
                .subscribe()
    }

    private fun getLiveData(favourites: List<Stop>) {
        for (favourite in favourites) {
            liveDataUseCase.getCondensedLiveData(favourite.id)
                    .compose(applyObservableSchedulers())
                    .doOnNext {
                        ifViewAttached { view -> view.showLiveData(favourites, favourite.id, it) }
                    }
                    .doOnError { Timber.e(it) }
                    .subscribe()
        }
    }

    fun updateFavourites() {

    }

}
