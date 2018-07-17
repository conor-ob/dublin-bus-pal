package ie.dublinbuspal.android.view.news.rss.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ie.dublinbuspal.android.R
import ie.dublinbuspal.domain.model.rss.RssNews
import kotlinx.android.synthetic.main.list_item_rss.view.*

class RssNewsItem(val rssNews: RssNews) : AbstractItem<RssNewsItem, RssNewsItem.ViewHolder>() {

    override fun getType() = R.id.view_rss_news_item

    override fun getLayoutRes() = R.layout.list_item_rss

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<RssNewsItem>(itemView) {

        override fun bindView(item: RssNewsItem, payloads: MutableList<Any>?) {
            itemView.title.text = item.rssNews.title
            itemView.description.text = item.rssNews.description
            itemView.publish_date.text = item.rssNews.timestamp.toString()
        }

        override fun unbindView(item: RssNewsItem?) {
            //TODO
        }

    }

}
