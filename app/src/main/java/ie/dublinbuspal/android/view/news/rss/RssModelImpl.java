package ie.dublinbuspal.android.view.news.rss;

import ie.dublinbuspal.android.data.remote.rss.xml.Rss;

public class RssModelImpl implements RssModel {

    private Rss rss;

    public RssModelImpl() {
        super();
    }

    @Override
    public Rss getRss() {
        return rss;
    }

    @Override
    public void setRss(Rss rss) {
        this.rss = rss;
    }

}
