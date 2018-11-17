package ie.dublinbuspal.view.favourites

import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.usecase.favourites.FavouritesUseCase
import ie.dublinbuspal.usecase.livedata.LiveDataUseCase
import ie.dublinbuspal.view.BasePresenter
import javax.inject.Inject

class FavouritesPresenter @Inject constructor(private val favouritesUseCase: FavouritesUseCase,
                                              private val liveDataUseCase: LiveDataUseCase) : BasePresenter<FavouritesView>() {

    fun start() {
        favouritesUseCase.getFavourites()
                .compose(applyObservableSchedulers())
                .doOnNext {
                    ifViewAttached { view -> view.showFavourites(it) }
//                    getLiveData(it)
                }
//                .doOnError { Timber.e(it) }
                .subscribe()
    }

    private fun getLiveData(favourites: List<FavouriteStop>) {
        for (favourite in favourites) {
            liveDataUseCase.getCondensedLiveData(favourite.id)
                    .compose(applyObservableSchedulers())
                    .doOnNext {
                        ifViewAttached { view -> view.showLiveData(favourites, favourite.id, it) }
                    }
//                    .doOnError { Timber.e(it) }
                    .subscribe()
        }
    }

    fun updateFavourites() {

    }

}
