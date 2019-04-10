package ie.dublinbuspal.service

import ie.dublinbuspal.service.api.rss.RssApi
import ie.dublinbuspal.service.api.rss.RssResponseXml
import ie.dublinbuspal.service.util.XmlUtils
import io.reactivex.Single

class MockDublinBusRssApi : RssApi {

    override fun getRssNews(): Single<RssResponseXml> {
        return singleResponse("rss_response.xml", RssResponseXml::class.java)
    }

    private fun <T> singleResponse(fileName: String, type: Class<T>): Single<T> {
        return Single.just(XmlUtils.deserialize(fileName, type))
    }

}
