package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.rss.DublinBusRssApi
import ie.dublinbuspal.service.api.rss.RssResponseXml
import io.reactivex.Single

class DublinBusRssResource(private val api: DublinBusRssApi) {

    fun getDublinBusNews(): Single<RssResponseXml> {
        return api.getRssNews()
    }

}
