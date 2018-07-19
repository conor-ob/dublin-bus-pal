package ie.dublinbuspal.android.view.news

import android.os.Bundle
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
        setupTabLayout(view)
        return view
    }

    override fun onAttach(view: View) {
        replaceTopController(view, TwitterController())
    }

    private fun setupTabLayout(view: View) {
        val tabLayout = view.tab_layout
        tabLayout.isTabIndicatorFullWidth = false
        tabLayout.setTabRippleColorResource(R.color.colorPrimaryRipple)
        tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator_rounded)
        tabLayout.setSelectedTabIndicatorGravity(0)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    replaceTopController(view, TwitterController())
                } else if (tab.position == 1) {
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
