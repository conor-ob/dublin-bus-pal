package ie.dublinbuspal.android.view.realtime;

import android.view.Menu;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import java.util.Set;

public interface RealTimePresenter extends MvpPresenter<RealTimeView> {

    void onResume(String stopId);

    void saveFavourite(String customName, Set<String> customRoutes);

    void routeFilterPressed(String route);

    void onCreateOptionsMenu(Menu menu);

    void onAddFavouritePressed();

    void removeFavourite();

    void onEditFavouritePressed();

    void onPause();

}
