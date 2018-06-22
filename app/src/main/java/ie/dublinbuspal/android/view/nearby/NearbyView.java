package ie.dublinbuspal.android.view.nearby;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.SortedMap;

public interface NearbyView extends MvpView {

    void launchRealTimeActivity(String stopId);

    void showNearbyStops(SortedMap<Double, DetailedBusStop> busStops);

    void hideProgress();

    void showButton();

}
