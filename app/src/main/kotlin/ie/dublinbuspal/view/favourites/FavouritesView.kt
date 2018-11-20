package ie.dublinbuspal.view.favourites

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.model.favourite.FavouriteStop
import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.LiveData

interface FavouritesView : MvpView {

    fun showFavourites(favourites: List<FavouriteStop>)

    fun showLiveData(favourites: List<FavouriteStop>, favouriteId: String, livedata: Map<Pair<String, Destination>, List<LiveData>>)

}
