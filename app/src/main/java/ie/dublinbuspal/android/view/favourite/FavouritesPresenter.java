package ie.dublinbuspal.android.view.favourite;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import java.util.List;

import ie.dublinbuspal.model.favourite.FavouriteStop;

public interface FavouritesPresenter extends MvpPresenter<FavouritesView> {

    void onResume();

    void onPause();

    void onPause(List<FavouriteStop> favourites);

}
