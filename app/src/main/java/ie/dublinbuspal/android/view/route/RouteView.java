package ie.dublinbuspal.android.view.route;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface RouteView extends MvpView {

    void setTitle(String origin, String destination);

    void displayBusStops(List<DetailedBusStop> busStops);

    void setTowards(String towards);

    void launchRealTimeActivity(String stopId);

    void hideProgress();

    void showError(int stringResource);

    void setBiDirectional(boolean bidirectional);

}
