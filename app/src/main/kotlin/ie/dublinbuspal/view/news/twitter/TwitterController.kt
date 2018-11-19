package ie.dublinbuspal.view.news.twitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import com.twitter.sdk.android.tweetui.TwitterListTimeline
import ie.dublinbuspal.android.R
import ie.dublinbuspal.view.BaseController
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_newsfeed.view.*

class TwitterController(args: Bundle) : BaseController(args) {

    private lateinit var swipeRefresh : SwipeRefreshLayout

    override fun getLayoutId() = R.layout.view_newsfeed

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupView(view)
        return view
    }

    private fun setupView(view: View) {
        swipeRefresh = view.swipe_refresh
        swipeRefresh.isRefreshing = true
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        Single.fromCallable { getAdapter(view) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    swipeRefresh.isRefreshing = false
                    view.recycler_view.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = it
                    }
                }
                .subscribe()
    }

    private fun getAdapter(view: View): TweetTimelineRecyclerViewAdapter {
        val timeline = TwitterListTimeline.Builder()
                .slugWithOwnerScreenName("dublin-bus", "dublinbuspal")
                .includeRetweets(true)
                .build()
        return TweetTimelineRecyclerViewAdapter.Builder(view.context)
                .setTimeline(timeline)
                .setViewStyle(R.style.tw__TweetLightStyle)
                .build()
    }

}
