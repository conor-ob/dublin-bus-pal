package ie.dublinbuspal.android

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import ie.dublinbuspal.android.di.ApplicationComponent
import ie.dublinbuspal.android.di.ApplicationModule
import ie.dublinbuspal.android.di.DaggerApplicationComponent
import ie.dublinbuspal.android.util.MetadataUtils
import ie.dublinbuspal.database.di.DatabaseModule
import ie.dublinbuspal.service.di.NetworkModule
import timber.log.Timber


class DublinBusApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        setupTimber()
        setupThreeTen()
        setupTwitter()
    }

    private fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .databaseModule(DatabaseModule(resources.getString(R.string.dublin_bus_database_name)))
                .networkModule(NetworkModule(
                        resources.getString(R.string.dublin_bus_soap_api_endpoint),
                        resources.getString(R.string.dublin_bus_rss_api_endpoint),
                        resources.getString(R.string.smart_dublin_rest_api_endpoint))
                )
                .build()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupThreeTen() {
        AndroidThreeTen.init(applicationContext)
    }

    private fun setupTwitter() {
        val config = TwitterConfig.Builder(applicationContext)
                .twitterAuthConfig(TwitterAuthConfig(
                        MetadataUtils.getMetadata(applicationContext,
                                "com.twitter.sdk.android.CONSUMER_KEY"),
                        MetadataUtils.getMetadata(applicationContext,
                                "com.twitter.sdk.android.CONSUMER_SECRET")))
                .build()
        Twitter.initialize(config)
    }

}
