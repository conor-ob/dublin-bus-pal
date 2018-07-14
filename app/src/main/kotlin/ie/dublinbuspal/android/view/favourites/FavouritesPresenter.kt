package ie.dublinbuspal.android.view.favourites

import android.util.Log
import ie.dublinbuspal.android.view.BasePresenter
import ie.dublinbuspal.domain.model.favourite.FavouriteStop
import ie.dublinbuspal.domain.usecase.favourites.FavouritesUseCase
import io.reactivex.Observable
import javax.inject.Inject

class FavouritesPresenter @Inject constructor(private val useCase: FavouritesUseCase) : BasePresenter<FavouritesView>() {

    fun start() {
//        Observable.fromCallable { useCase.insertFavourite(FavouriteStop("769", "Poo", mutableListOf("d", "512"), 1)) }
//                .compose(applySchedulers())
//                .subscribe()

        useCase.getFavourites()
                .compose(applySchedulers())
                .doOnNext { Log.i(javaClass.simpleName, it.toString()) }
                .doOnError { Log.e(javaClass.simpleName, it.message, it) }
                .subscribe()
    }

    fun updateFavourites() {

    }

}
