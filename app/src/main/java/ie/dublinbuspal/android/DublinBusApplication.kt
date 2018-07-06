package ie.dublinbuspal.android

import android.app.Application
import ie.dublinbuspal.android.di.ApplicationComponent
import ie.dublinbuspal.android.di.ApplicationModule
import ie.dublinbuspal.android.di.DaggerApplicationComponent

class DublinBusApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    private fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }

}
