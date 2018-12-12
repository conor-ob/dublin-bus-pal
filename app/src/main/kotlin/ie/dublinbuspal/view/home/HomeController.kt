package ie.dublinbuspal.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import ie.dublinbuspal.android.R
import ie.dublinbuspal.view.BaseController
import ie.dublinbuspal.view.favourites.FavouritesController
import ie.dublinbuspal.view.nearby.NearbyController
import ie.dublinbuspal.view.news.NewsController
import ie.dublinbuspal.view.search.SearchController
import kotlinx.android.synthetic.main.view_home.view.*
import timber.log.Timber

class HomeController(args: Bundle) : BaseController(args) {

    override fun getLayoutId() = R.layout.view_home

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)
        setupBottomNavigation(view)
        return view
    }

    private fun setupBottomNavigation(view: View) {
        view.bottom_navigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.navigation_news -> replaceTopController(view, NewsController(Bundle.EMPTY))
                R.id.navigation_nearby -> replaceTopController(view, NearbyController(Bundle.EMPTY))
                R.id.navigation_favourites -> replaceTopController(view, FavouritesController(Bundle.EMPTY))
                R.id.navigation_search -> replaceTopController(view, SearchController(Bundle.EMPTY))
                else -> false
            }
        }
        view.bottom_navigation.setOnNavigationItemReselectedListener {
//            Timber.d("Bottom navigation item reselected")
        }
        view.bottom_navigation.selectedItemId = R.id.navigation_nearby
    }

    private fun replaceTopController(view: View, controller: Controller): Boolean {
        val childRouter = getChildRouter(view.container)
        if (!childRouter.hasRootController()) {
            childRouter.setRoot(RouterTransaction.with(controller))
        } else {
            childRouter.pushController(RouterTransaction
                    .with(controller)
                    .pushChangeHandler(FadeChangeHandler(500L))
                    .popChangeHandler(FadeChangeHandler(500L))
            )
        }
        return childRouter.hasRootController()
    }

}
