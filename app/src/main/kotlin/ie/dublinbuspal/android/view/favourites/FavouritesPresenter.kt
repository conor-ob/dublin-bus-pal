package ie.dublinbuspal.android.view.favourites

import android.util.Log
import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.domain.usecase.favourites.FavouritesUseCase
import timber.log.Timber
import javax.inject.Inject

class FavouritesPresenter @Inject constructor(private val useCase: FavouritesUseCase) : BasePresenter<FavouritesView>() {

    fun start() {
        useCase.getFavourites()
                .compose(applyObservableSchedulers())
                .doOnNext { ifViewAttached { view -> view.showFavourites(it) } }
                .doOnError { Timber.e(it) }
                .subscribe()
    }

    fun updateFavourites() {

    }

}
