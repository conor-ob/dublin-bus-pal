package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.DublinBusRssApi
import ie.dublinbuspal.service.model.rss.RssResponseXml
import io.reactivex.Single

class DublinBusRssResourceAdapter(
        private val api: DublinBusRssApi
) : DublinBusRssResource {

    override fun getDublinBusNews(): Single<RssResponseXml> {
        return api.getRssNews()
    }

}
