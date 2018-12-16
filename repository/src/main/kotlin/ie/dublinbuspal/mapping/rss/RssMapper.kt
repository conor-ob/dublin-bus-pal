package ie.dublinbuspal.mapping.rss

import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.rss.NewsItemXml
import ie.dublinbuspal.util.TimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class RssMapper : Mapper<NewsItemXml, RssNews> {

    override fun map(from: NewsItemXml): RssNews {
        val time = TimeUtils.toInstant(from.pubDate!!)
        return RssNews(from.title!!, from.link!!, from.description!!, TimeUtils.toString(Instant.now(), time))
    }

}
