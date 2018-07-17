package ie.dublinbuspal.domain.mapping.rss

import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.domain.model.rss.RssNews
import ie.dublinbuspal.service.model.rss.NewsItemXml
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class RssMapper : Mapper<NewsItemXml, RssNews> {

    private val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.UK)

    override fun map(from: NewsItemXml): RssNews {
//        val time = LocalDateTime.parse(from.pubDate, formatter)
        return RssNews(from.title!!, from.link!!, from.description!!, Instant.now()) //TODO
    }

}
