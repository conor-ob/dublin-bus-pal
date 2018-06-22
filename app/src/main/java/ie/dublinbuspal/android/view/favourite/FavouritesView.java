package ie.dublinbuspal.android.view.favourite;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface FavouritesView extends MvpView {

    void showFavourites(List<DetailedBusStop> favouriteBusStops);

    void launchRealTimeActivity(String stopId);

    void hideProgress();

    void showError(int stringId);

}
