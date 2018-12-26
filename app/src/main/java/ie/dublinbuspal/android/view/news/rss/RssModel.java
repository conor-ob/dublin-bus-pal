package ie.dublinbuspal.android.view.news.rss;

import java.util.List;

import ie.dublinbuspal.model.rss.RssNews;

public interface RssModel {

    List<RssNews> getRss();

    void setRss(List<RssNews> rss);

}
