package ie.dublinbuspal.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import ie.dublinbuspal.android.util.BottomNavigationUtils
import ie.dublinbuspal.android.view.favourites.FavouritesController
import ie.dublinbuspal.android.view.nearby.NearbyController
import ie.dublinbuspal.android.view.news.NewsController
import ie.dublinbuspal.android.view.search.SearchController
import kotlinx.android.synthetic.main.activity_home.*

class DublinBusActivity : AppCompatActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        setupRouter(savedInstanceState)
    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(NearbyController(Bundle.EMPTY)))
        }
    }

    private fun setupBottomNavigation() {
        BottomNavigationUtils.disableShiftMode(bottom_navigation)
        bottom_navigation.menu.getItem(1).isChecked = true
        bottom_navigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.navigation_news -> replaceTopController(NewsController(Bundle.EMPTY))
                R.id.navigation_nearby -> replaceTopController(NearbyController(Bundle.EMPTY))
                R.id.navigation_favourites -> replaceTopController(FavouritesController(Bundle.EMPTY))
                R.id.navigation_search -> replaceTopController(SearchController(Bundle.EMPTY))
                else -> false
            }
        }
    }

    private fun replaceTopController(controller: Controller): Boolean {
        router.replaceTopController(RouterTransaction
                .with(controller)
                .pushChangeHandler(FadeChangeHandler(500L))
                .popChangeHandler(FadeChangeHandler(500L))
        )
        return router.hasRootController()
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

}
