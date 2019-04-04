package ie.dublinbuspal.service.api.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item")
data class NewsItemXml(
        @field:Element(name = "title", required = false) var title: String? = null,
        @field:Element(name = "link", required = false) var link: String? = null,
        @field:Element(name = "description", required = false) var description: String? = null,
        @field:Element(name = "guid", required = false) var guid: String? = null,
        @field:Element(name = "pubDate", required = false) var pubDate: String? = null,
        @field:Element(name = "category", required = false) var category: String? = null
)
