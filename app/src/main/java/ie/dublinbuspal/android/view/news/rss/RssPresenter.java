package ie.dublinbuspal.android.view.news.rss;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface RssPresenter extends MvpPresenter<RssView> {

    void onResume();

    void onRefresh();

    void onDestroy();

}
