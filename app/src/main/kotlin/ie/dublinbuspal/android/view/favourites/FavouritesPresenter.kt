package ie.dublinbuspal.android.view.favourites

import com.hannesdorfmann.mosby3.mvp.MvpPresenter

interface FavouritesPresenter : MvpPresenter<FavouritesView> {

    fun onResume()

    fun onPause(shouldSaveFavourites: Boolean)

    fun onFavouritesReordered(position1: Int, position2: Int)

}
