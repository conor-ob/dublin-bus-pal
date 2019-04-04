package ie.dublinbuspal.service.api.rss

import io.reactivex.Single
import retrofit2.http.GET

interface DublinBusRssApi {

    @GET("RSS/Rss-news/")
    fun getRssNews(): Single<RssResponseXml>

}
