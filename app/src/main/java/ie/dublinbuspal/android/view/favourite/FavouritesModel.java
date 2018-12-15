package ie.dublinbuspal.android.view.favourite;

import java.util.List;

import ie.dublinbuspal.model.favourite.FavouriteStop;

public interface FavouritesModel {

    void setFavourites(List<FavouriteStop> favourites);

    List<FavouriteStop> getFavourites();

}
