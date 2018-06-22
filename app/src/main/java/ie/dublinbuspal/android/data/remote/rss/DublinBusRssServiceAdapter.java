package ie.dublinbuspal.android.data.remote.rss;

import ie.dublinbuspal.android.data.remote.rss.xml.Rss;

import retrofit2.Response;

public class DublinBusRssServiceAdapter implements RssDataSource {

    private final DublinBusRssApi api;

    public DublinBusRssServiceAdapter(DublinBusRssApi api) {
        this.api = api;
    }

    @Override
    public Rss getRss() throws Exception {
        Response<Rss> response = api.getRssNews().execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new Exception();
    }

}
