package ie.dublinbuspal.android

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import ie.dublinbuspal.android.di.ApplicationComponent
import ie.dublinbuspal.android.di.ApplicationModule
import ie.dublinbuspal.android.di.DaggerApplicationComponent

class DublinBusApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        setupThreeTen()
    }

    private fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }

    private fun setupThreeTen() {
        AndroidThreeTen.init(applicationContext)
    }

}
