package ie.dublinbuspal.android.view.news

import android.os.Bundle
import android.support.design.widget.TabItem
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.view.BaseController
import ie.dublinbuspal.android.view.news.rss.RssNewsController
import ie.dublinbuspal.android.view.news.twitter.TwitterController
import kotlinx.android.synthetic.main.view_news.view.*
import timber.log.Timber

class NewsController : BaseController() {

    override fun getLayoutId() = R.layout.view_news

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupTabs(view)
        return view
    }

    private fun setupTabs(view: View) {
        val tabs = view.findViewById(R.id.tabs) as TabLayout
        val tab1 = tabs.getTabAt(0) as TabLayout.Tab
        tab1.tag = "twitter"
        val tab2 = tabs.getTabAt(1) as TabLayout.Tab
        tab2.tag = "rss"
//        val tab1 = view.findViewById(R.id.tabItem) as TabItem
//        val tab2 = view.findViewById(R.id.tabItem2) as TabItem
//        tab1.setOnClickListener { replaceTopController(view, TwitterController()) }
//        tab2.setOnClickListener { replaceTopController(view, RssNewsController(Bundle.EMPTY)) }

        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Timber.d("selected")
                if (tab.tag == "twitter") {
                    replaceTopController(view, TwitterController())
                } else if (tab.tag == "rss") {
                    replaceTopController(view, RssNewsController(Bundle.EMPTY))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Timber.d("reselected")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Timber.d("unselected")
            }

        })
    }

    private fun replaceTopController(view: View, controller: Controller) {
        val childRouter = getChildRouter(view.news_container)
        if (!childRouter.hasRootController()) {
            childRouter.setRoot(RouterTransaction.with(controller))
        } else {
            childRouter.replaceTopController(RouterTransaction
                    .with(controller)
                    .pushChangeHandler(FadeChangeHandler(500L))
                    .popChangeHandler(FadeChangeHandler(500L))
            )
        }
    }

}
