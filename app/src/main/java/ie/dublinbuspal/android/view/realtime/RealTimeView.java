package ie.dublinbuspal.android.view.realtime;

import android.view.Menu;

import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;
import java.util.Set;

public interface RealTimeView extends MvpView {

    void showBusStop(DetailedBusStop busStop);

    void showRealTimeData(List<RealTimeData> realTimeData);

    void showBusStopService(BusStopService busStopService);

    void launchRouteActivity(String routeId);

    void onFavouriteSaved();

    void showProgress();

    void hideProgress();

    void applyRouteFiltering(Set<String> routeFilters);

    void onCreateFavouriteOptionsMenu(Menu menu);

    void onCreateDefaultOptionsMenu(Menu menu);

    void presentSaveFavouriteDialog(DetailedBusStop busStop, BusStopService service);

    void onFavouriteRemoved();

    void showStreetView(DetailedBusStop busStop);

    void showError(int stringId);

}
