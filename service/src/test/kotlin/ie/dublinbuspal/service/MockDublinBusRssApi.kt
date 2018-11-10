package ie.dublinbuspal.service

import ie.dublinbuspal.service.model.rss.RssResponseXml
import ie.dublinbuspal.service.util.XmlUtils
import io.reactivex.Single

class MockDublinBusRssApi : DublinBusRssApi {

    override fun getRssNews(): Single<RssResponseXml> {
        return singleResponse("rss_response.xml", RssResponseXml::class.java)
    }

    private fun <T> singleResponse(fileName: String, type: Class<T>): Single<T> {
        return Single.just(XmlUtils.deserialize(fileName, type))
    }

}
