package ie.dublinbuspal.android.view.nearby;

import android.location.Location;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface NearbyPresenter extends MvpPresenter<NearbyView> {

    void refreshNearby(Location location);

    void onPause();

}
