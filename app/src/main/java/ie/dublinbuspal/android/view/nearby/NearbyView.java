package ie.dublinbuspal.android.view.nearby;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.SortedMap;

import ie.dublinbuspal.model.stop.Stop;
import ie.dublinbuspal.util.Coordinate;

public interface NearbyView extends MvpView {

    void launchRealTimeActivity(String stopId);

    void showNearbyStops(SortedMap<Double, Stop> busStops);

    void hideProgress();

    void showButton();

    void updateLocation(Coordinate coordinate);

}
