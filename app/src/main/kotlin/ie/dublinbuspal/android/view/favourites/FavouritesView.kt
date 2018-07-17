package ie.dublinbuspal.android.view.favourites

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.domain.model.favourite.FavouriteStop

interface FavouritesView : MvpView {

    fun showFavourites(favourites: List<FavouriteStop>)

}
