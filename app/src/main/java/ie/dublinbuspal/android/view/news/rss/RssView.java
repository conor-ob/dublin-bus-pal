package ie.dublinbuspal.android.view.news.rss;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ie.dublinbuspal.model.rss.RssNews;

public interface RssView extends MvpView {

    void showRss(List<RssNews> items);

    void hideProgress();

    void showError(int stringId);

}
