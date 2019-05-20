package ie.dublinbuspal.service.api.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss")
data class RssResponseXml(
        @field:Attribute(name = "version", required = false) var version: String? = null,
        @field:Element(name = "channel", required = false) var channel: ChannelXml? = null
)

@Root(name = "channel")
data class ChannelXml(
        @field:Element(name = "title", required = false) var title: String? = null,
        @field:Element(name = "link", required = false) var link: String? = null,
        @field:Element(name = "description", required = false) var description: String? = null,
        @field:ElementList(name = "item", required = false, inline = true) var newsItems: List<NewsItemXml> = mutableListOf()
)

@Root(name = "item")
data class NewsItemXml(
        @field:Element(name = "title", required = false) var title: String? = null,
        @field:Element(name = "link", required = false) var link: String? = null,
        @field:Element(name = "description", required = false) var description: String? = null,
        @field:Element(name = "guid", required = false) var guid: String? = null,
        @field:Element(name = "pubDate", required = false) var pubDate: String? = null,
        @field:Element(name = "category", required = false) var category: String? = null
)
