package ie.dublinbuspal.android.view.favourite;

import java.util.List;

import ie.dublinbuspal.model.favourite.FavouriteStop;

public class FavouritesModelImpl implements FavouritesModel {

    private List<FavouriteStop> favourites;

    public FavouritesModelImpl() {
        super();
    }

    @Override
    public void setFavourites(List<FavouriteStop> favourites) {
        this.favourites = favourites;
    }

    @Override
    public List<FavouriteStop> getFavourites() {
        return favourites;
    }

}
