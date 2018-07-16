package ie.dublinbuspal.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
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

    private fun setupBottomNavigation() {
        BottomNavigationUtils.disableShiftMode(bottom_navigation)
        bottom_navigation.menu.getItem(1).isChecked = true
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_news -> news()
                R.id.navigation_nearby -> nearby()
                R.id.navigation_favourites -> favourites()
                R.id.navigation_search -> search()
                else -> false
            }
        }
    }

    private fun news(): Boolean {
        router.replaceTopController(RouterTransaction
                .with(NewsController(Bundle.EMPTY))
                .pushChangeHandler(FadeChangeHandler(500L))
                .popChangeHandler(FadeChangeHandler(500L))
        )
        return true
    }

    private fun nearby(): Boolean {
        router.replaceTopController(RouterTransaction
                .with(NearbyController(Bundle.EMPTY))
                .pushChangeHandler(FadeChangeHandler(500L))
                .popChangeHandler(FadeChangeHandler(500L))
        )
        return true
    }

    private fun favourites(): Boolean {
        router.replaceTopController(RouterTransaction
                .with(FavouritesController(Bundle.EMPTY))
                .pushChangeHandler(FadeChangeHandler(500L))
                .popChangeHandler(FadeChangeHandler(500L))
        )
        return true
    }

    private fun search(): Boolean {
        router.replaceTopController(RouterTransaction
                .with(SearchController(Bundle.EMPTY))
                .pushChangeHandler(FadeChangeHandler(500L))
                .popChangeHandler(FadeChangeHandler(500L))
        )
        return true
    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(NearbyController(Bundle.EMPTY)))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

}
