package ie.dublinbuspal.android.view.favourite;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ie.dublinbuspal.model.favourite.FavouriteStop;

public interface FavouritesView extends MvpView {

    void showFavourites(List<FavouriteStop> favouriteBusStops);

    void launchRealTimeActivity(String stopId);

    void hideProgress();

    void showError(int stringId);

}
