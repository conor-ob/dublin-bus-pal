package ie.dublinbuspal.mapping.rss

import ie.dublinbuspal.model.rss.RssNews
import ie.dublinbuspal.model.rss.RssNewsAge
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.rss.NewsItemXml
import ie.dublinbuspal.util.TimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class RssMapper : Mapper<NewsItemXml, RssNews> {

    override fun map(from: NewsItemXml): RssNews {
        val time = TimeUtils.toInstant(from.pubDate!!)
        return RssNews(from.title!!, from.link!!, from.description!!, mapRssAge(time))
    }

    private fun mapRssAge(time: Instant): RssNewsAge {
        val now = TimeUtils.now()
        val timestamp = TimeUtils.toString(now, time)
        val age = TimeUtils.secondsBetween(time, now)
        return RssNewsAge(timestamp, age)
    }

}
