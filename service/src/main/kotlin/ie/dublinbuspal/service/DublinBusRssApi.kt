package ie.dublinbuspal.service

import ie.dublinbuspal.service.model.rss.RssResponseXml
import io.reactivex.Single
import retrofit2.http.GET

interface DublinBusRssApi {

    @GET("RSS/Rss-news/")
    fun getRssNews(): Single<RssResponseXml>

}
