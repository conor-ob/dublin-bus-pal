package ie.dublinbuspal.android.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import ie.dublinbuspal.android.R
import ie.dublinbuspal.android.util.BottomNavigationUtils
import ie.dublinbuspal.android.view.favourites.FavouritesController
import ie.dublinbuspal.android.view.nearby.NearbyController
import ie.dublinbuspal.android.view.news.NewsController
import ie.dublinbuspal.android.view.search.SearchController
import kotlinx.android.synthetic.main.view_home.view.*

class HomeController : Controller() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.view_home, container, false)
        setupBottomNavigation(view)
        return view
    }

    private fun setupBottomNavigation(view: View) {
        BottomNavigationUtils.disableShiftMode(view.bottom_navigation)
        view.bottom_navigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.navigation_news -> replaceTopController(view, NewsController(Bundle.EMPTY))
                R.id.navigation_nearby -> replaceTopController(view, NearbyController(Bundle.EMPTY))
                R.id.navigation_favourites -> replaceTopController(view, FavouritesController(Bundle.EMPTY))
                R.id.navigation_search -> replaceTopController(view, SearchController(Bundle.EMPTY))
                else -> false
            }
        }
        view.bottom_navigation.selectedItemId = R.id.navigation_nearby
        view.bottom_navigation.menu.getItem(1).isChecked = true
    }

    private fun replaceTopController(view: View, controller: Controller): Boolean {
        val childRouter = getChildRouter(view.container)
        if (!childRouter.hasRootController()) {
            childRouter.setRoot(RouterTransaction.with(controller))
        } else {
            childRouter.replaceTopController(RouterTransaction
                    .with(controller)
                    .pushChangeHandler(FadeChangeHandler(500L))
                    .popChangeHandler(FadeChangeHandler(500L))
            )
        }
        return childRouter.hasRootController()
    }

}
