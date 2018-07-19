package ie.dublinbuspal.android.view.news.twitter

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import com.twitter.sdk.android.tweetui.TwitterListTimeline
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseController
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_newsfeed.view.*


class TwitterController : BaseController() {

    override fun getLayoutId() = R.layout.view_newsfeed

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        Single.fromCallable { getAdapter(view) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
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
