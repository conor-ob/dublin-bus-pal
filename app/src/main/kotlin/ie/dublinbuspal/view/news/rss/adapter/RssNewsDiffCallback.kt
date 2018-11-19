package ie.dublinbuspal.view.news.rss.adapter

import com.mikepenz.fastadapter.commons.utils.DiffCallback

class RssNewsDiffCallback : DiffCallback<RssNewsItem> {

    override fun areItemsTheSame(oldItem: RssNewsItem, newItem: RssNewsItem): Boolean {
        return oldItem.rssNews.title == oldItem.rssNews.title
    }

    override fun areContentsTheSame(oldItem: RssNewsItem, newItem: RssNewsItem): Boolean {
        return oldItem.rssNews == newItem.rssNews
    }

    override fun getChangePayload(oldItem: RssNewsItem?, oldItemPosition: Int, newItem: RssNewsItem?, newItemPosition: Int): Any? {
        return null
    }
}
