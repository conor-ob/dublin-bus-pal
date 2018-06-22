package ie.dublinbuspal.android.data.remote.rss;

import ie.dublinbuspal.android.data.remote.rss.xml.Rss;

public interface RssDataSource {

    Rss getRss() throws Exception;

}
