package ie.dublinbuspal.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import ie.dublinbuspal.android.view.home.HomeController
import kotlinx.android.synthetic.main.activity_root.*

class DublinBusActivity : AppCompatActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setupRouter(savedInstanceState)
    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, root_container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(HomeController()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

}
