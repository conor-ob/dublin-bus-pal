package ie.dublinbuspal.service.model.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel")
data class ChannelXml(
        @field:Element(name = "title", required = false) var title: String? = null,
        @field:Element(name = "link", required = false) var link: String? = null,
        @field:Element(name = "description", required = false) var description: String? = null,
        @field:ElementList(name = "item", required = false, inline = true) var newsItems: List<NewsItemXml> = mutableListOf()
)
