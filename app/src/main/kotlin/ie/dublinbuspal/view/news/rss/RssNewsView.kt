package ie.dublinbuspal.view.news.rss

import com.hannesdorfmann.mosby3.mvp.MvpView
import ie.dublinbuspal.model.rss.RssNews

interface RssNewsView : MvpView {

    fun showRssNews(rssNews: List<RssNews>)

}
