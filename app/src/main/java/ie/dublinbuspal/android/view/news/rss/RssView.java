package ie.dublinbuspal.android.view.news.rss;

import ie.dublinbuspal.android.data.remote.rss.xml.Item;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface RssView extends MvpView {

    void showRss(List<Item> items);

    void hideProgress();

    void showError(int stringId);

}
