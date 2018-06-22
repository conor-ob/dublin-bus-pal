package ie.dublinbuspal.android.data.remote.rss;

import ie.dublinbuspal.android.data.remote.rss.xml.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DublinBusRssApi {

    String BASE_URL = "http://www.dublinbus.ie/";

    @GET("RSS/Rss-news/")
    Call<Rss> getRssNews();

}
