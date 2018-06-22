package ie.dublinbuspal.android.view.route;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface RoutePresenter extends MvpPresenter<RouteView> {

    void onResume(String routeId, String stopId);

    void changeDirectionPressed();

    void onDestroy();

}
