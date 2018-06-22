package ie.dublinbuspal.android.view.favourite;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;

import java.util.List;

public class FavouritesModelImpl implements FavouritesModel {

    private List<DetailedBusStop> favourites;

    public FavouritesModelImpl() {
        super();
    }

    @Override
    public void setFavourites(List<DetailedBusStop> favourites) {
        this.favourites = favourites;
    }

    @Override
    public List<DetailedBusStop> getFavourites() {
        return favourites;
    }

}
