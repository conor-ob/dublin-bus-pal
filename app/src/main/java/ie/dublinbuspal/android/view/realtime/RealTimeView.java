package ie.dublinbuspal.android.view.realtime;

import android.view.Menu;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;
import java.util.Set;

import ie.dublinbuspal.model.livedata.LiveData;
import ie.dublinbuspal.model.stop.Stop;

public interface RealTimeView extends MvpView {

    void showBusStop(Stop busStop);

    void showRealTimeData(List<LiveData> realTimeData);

    void showBusStopService(List<String> busStopService);

    void launchRouteActivity(String routeId, String operator);

    void onFavouriteSaved();

    void showProgress();

    void hideProgress();

    void applyRouteFiltering(Set<String> routeFilters);

    void onCreateFavouriteOptionsMenu(Menu menu);

    void onCreateDefaultOptionsMenu(Menu menu);

    void presentSaveFavouriteDialog(Stop busStop, List<String> service);

    void onFavouriteRemoved();

    void showStreetView(Stop busStop);

    void showError(int stringId);

}
