package ie.dublinbuspal.android.view.route;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface RouteServicePresenter extends MvpPresenter<RouteServiceView> {

    void onResume(String routeId, String operator, String stopId);

    void onNextVariantPressed();

    void onDestroy();

    void onMapReady();

}
