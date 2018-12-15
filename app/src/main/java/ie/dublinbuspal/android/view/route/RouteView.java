package ie.dublinbuspal.android.view.route;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ie.dublinbuspal.model.stop.Stop;

public interface RouteView extends MvpView {

    void setTitle(String origin, String destination);

    void displayBusStops(List<Stop> busStops);

    void setTowards(String towards);

    void launchRealTimeActivity(String stopId);

    void hideProgress();

    void showError(int stringResource);

    void setBiDirectional(boolean bidirectional);

}
