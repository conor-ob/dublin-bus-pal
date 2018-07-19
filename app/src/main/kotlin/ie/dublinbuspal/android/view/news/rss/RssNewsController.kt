package ie.dublinbuspal.android.view.news.rss

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseMvpController
import ie.dublinbuspal.android.view.news.rss.adapter.RssNewsDiffCallback
import ie.dublinbuspal.android.view.news.rss.adapter.RssNewsItem
import ie.dublinbuspal.domain.model.rss.RssNews
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_newsfeed.view.*
import java.util.*

class RssNewsController(args: Bundle) : BaseMvpController<RssNewsView, RssNewsPresenter>(args), RssNewsView {

    private lateinit var fastAdapter: FastAdapter<IItem<Any, RecyclerView.ViewHolder>>
    private lateinit var rssNewsAdapter: ItemAdapter<RssNewsItem>

    override fun getLayoutId() = R.layout.view_newsfeed

    override fun createPresenter(): RssNewsPresenter {
        return applicationComponent()?.rssNewsPresenter()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupRecyclerView(view)
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.start()
    }

    private fun setupRecyclerView(view: View) {
        rssNewsAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(Arrays.asList(rssNewsAdapter))
        fastAdapter.withSelectable(true)
        view.recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = fastAdapter
        }
    }

    override fun showRssNews(rssNews: List<RssNews>) {
        Single.fromCallable { rssNews.map { RssNewsItem(it) } }
                .map { FastAdapterDiffUtil.calculateDiff(rssNewsAdapter, it, RssNewsDiffCallback()) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { FastAdapterDiffUtil.set(rssNewsAdapter, it) }
    }

}
