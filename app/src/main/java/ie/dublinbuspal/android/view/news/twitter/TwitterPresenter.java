package ie.dublinbuspal.android.view.news.twitter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface TwitterPresenter extends MvpPresenter<TwitterView> {

    void onResume();

    void onRefresh();

    void onDestroy();

}
