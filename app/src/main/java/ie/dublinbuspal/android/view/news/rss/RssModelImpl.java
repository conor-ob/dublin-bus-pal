package ie.dublinbuspal.android.view.news.rss;

import java.util.List;

import ie.dublinbuspal.model.rss.RssNews;

public class RssModelImpl implements RssModel {

    private List<RssNews> rss;

    public RssModelImpl() {
        super();
    }

    @Override
    public List<RssNews> getRss() {
        return rss;
    }

    @Override
    public void setRss(List<RssNews> rss) {
        this.rss = rss;
    }

}
