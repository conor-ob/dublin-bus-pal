package ie.dublinbuspal.domain.model.rss

import org.threeten.bp.Instant

data class RssNews(
        val title: String,
        val link: String,
        val description: String,
        val timestamp: Instant
)
