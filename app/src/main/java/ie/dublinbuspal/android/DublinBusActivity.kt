package ie.dublinbuspal.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import ie.dublinbuspal.android.view.nearby.NearbyController
import kotlinx.android.synthetic.main.activity_home.*

class DublinBusActivity : AppCompatActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupRouter(savedInstanceState)
    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction
                    .with(NearbyController
                            .Builder(53.347335, -6.259137, 15.9F) //TODO get last known location
                            .build()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

}
