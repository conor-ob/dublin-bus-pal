package ie.dublinbuspal.android.view.favourites

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import ie.dublinbuspal.model.favourite.FavouriteStop

interface FavouritesPresenter : MvpPresenter<FavouritesView> {

    fun onResume()

    fun onPause()

    fun onPause(favourites: List<FavouriteStop>)

}
