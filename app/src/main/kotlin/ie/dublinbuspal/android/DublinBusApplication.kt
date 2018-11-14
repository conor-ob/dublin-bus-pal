package ie.dublinbuspal.android

import android.app.Application
import ie.dublinbuspal.di.*

class DublinBusApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    private fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .databaseModule(DatabaseModule(resources.getString(R.string.dublin_bus_database_name))) //TODO check database name of existing app
                .networkModule(NetworkModule(
                        resources.getString(R.string.dublin_bus_soap_api_endpoint),
                        resources.getString(R.string.dublin_bus_rss_api_endpoint),
                        resources.getString(R.string.smart_dublin_rest_api_endpoint))
                )
                .build()
    }

}
