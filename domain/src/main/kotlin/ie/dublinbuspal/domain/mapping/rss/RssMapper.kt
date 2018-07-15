package ie.dublinbuspal.domain.mapping.rss

import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.domain.model.rss.NewsItem
import ie.dublinbuspal.service.model.rss.NewsItemXml
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class RssMapper : Mapper<NewsItemXml, NewsItem> {

    private val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")

    override fun map(from: NewsItemXml): NewsItem {
        val time = LocalDateTime.parse(from.pubDate, formatter)
        return NewsItem(from.title!!, from.link!!, from.description!!, Instant.now()) //TODO
    }

}
