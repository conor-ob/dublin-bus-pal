package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.model.rss.RssResponseXml
import io.reactivex.Single

interface DublinBusRssResource {

    fun getDublinBusNews(): Single<RssResponseXml>

}
