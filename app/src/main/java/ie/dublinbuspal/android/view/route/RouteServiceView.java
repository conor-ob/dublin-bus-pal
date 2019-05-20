package ie.dublinbuspal.android.view.route;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import org.jetbrains.annotations.NotNull;

import ie.dublinbuspal.android.view.routeservice.RouteServiceViewModel;

public interface RouteServiceView extends MvpView {

    void launchRealTimeActivity(String stopId);

    void render(@NotNull RouteServiceViewModel viewModel);

}
