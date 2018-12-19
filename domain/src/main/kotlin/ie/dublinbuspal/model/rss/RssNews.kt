package ie.dublinbuspal.model.rss

data class RssNews(
        val title: String,
        val link: String,
        val description: String,
        val age: RssNewsAge
)
