package ie.dublinbuspal.android.view.favourite;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;

import java.util.List;

public interface FavouritesModel {

    void setFavourites(List<DetailedBusStop> favourites);

    List<DetailedBusStop> getFavourites();

}
