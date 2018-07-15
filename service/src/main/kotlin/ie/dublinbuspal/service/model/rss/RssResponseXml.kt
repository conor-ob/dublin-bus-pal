package ie.dublinbuspal.service.model.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss")
data class RssResponseXml(
        @field:Attribute(name = "version", required = false) var version: String? = null,
        @field:Element(name = "channel", required = false) var channel: ChannelXml? = null
)
