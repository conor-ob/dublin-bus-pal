package ie.dublinbuspal.android.view.favourites

import ie.dublinbuspal.model.favourite.FavouriteStop

interface FavouritesModel {

    fun setFavourites(favourites: List<FavouriteStop>)

    fun getFavourites(): List<FavouriteStop>

}
