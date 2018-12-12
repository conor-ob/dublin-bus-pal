package ie.dublinbuspal.android.view.realtime;

import android.view.Menu;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;
import java.util.Set;

import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.model.livedata.LiveData;
import ie.dublinbuspal.model.stop.ResolvedStop;

public interface RealTimeView extends MvpView {

    void showBusStop(ResolvedStop busStop);

    void showRealTimeData(List<LiveData> realTimeData);

    void showBusStopService(BusStopService busStopService);

    void launchRouteActivity(String routeId);

    void onFavouriteSaved();

    void showProgress();

    void hideProgress();

    void applyRouteFiltering(Set<String> routeFilters);

    void onCreateFavouriteOptionsMenu(Menu menu);

    void onCreateDefaultOptionsMenu(Menu menu);

    void presentSaveFavouriteDialog(ResolvedStop busStop, BusStopService service);

    void onFavouriteRemoved();

    void showStreetView(ResolvedStop busStop);

    void showError(int stringId);

}
