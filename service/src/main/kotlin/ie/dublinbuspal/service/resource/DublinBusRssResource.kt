package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.rss.RssApi
import ie.dublinbuspal.service.api.rss.RssResponseXml
import io.reactivex.Single

class DublinBusRssResource(private val api: RssApi) {

    fun getDublinBusNews(): Single<RssResponseXml> {
        return api.getRssNews()
    }

}
