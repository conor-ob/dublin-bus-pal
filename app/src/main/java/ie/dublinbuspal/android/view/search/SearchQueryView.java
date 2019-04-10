package ie.dublinbuspal.android.view.search;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface SearchQueryView extends MvpView {

    void launchRouteActivity(String routeId, String operator);

    void launchRealTimeActivity(String stopId);

    void showSearchResult(List<Object> searchResult);

    void showLoading();

    void hideLoading();

    void showError(int stringResource);
}
