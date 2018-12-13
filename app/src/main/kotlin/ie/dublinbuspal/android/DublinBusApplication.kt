package ie.dublinbuspal.android

import android.app.Application
import android.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import ie.dublinbuspal.android.util.ErrorLog
import ie.dublinbuspal.di.*
import ie.dublinbuspal.util.MetadataUtils
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class DublinBusApplication : Application() {

    lateinit var oldApplicationComponent: ie.dublinbuspal.android.di.ApplicationComponent
    lateinit var applicationComponent: ApplicationComponent

    init {
        RxJavaPlugins.setErrorHandler { ErrorLog.u(it) }
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupThreeTen()
        setupDagger()
        setupTwitter()
        setupAnalytics()
        setupPreferences()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupThreeTen() {
        AndroidThreeTen.init(applicationContext)
    }

    private fun setupDagger() {
        oldApplicationComponent = ie.dublinbuspal.android.di.DaggerApplicationComponent.builder()
                .applicationModule(ie.dublinbuspal.android.di.ApplicationModule(applicationContext))
                .build()
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .databaseModule(DatabaseModule(resources.getString(R.string.dublin_bus_database_name))) //TODO check database name of existing app
                .networkModule(NetworkModule(
                        resources.getString(R.string.dublin_bus_soap_api_endpoint),
                        resources.getString(R.string.smart_dublin_rest_api_endpoint),
                        resources.getString(R.string.dublin_bus_rss_api_endpoint)
                )
                )
                .build()
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

    private fun setupAnalytics() {
        //        Fabric.with(getApplicationContext(), new Crashlytics(), new Answers());
    }

    private fun setupPreferences() {
        PreferenceManager.setDefaultValues(applicationContext, ie.dublinbuspal.android.R.xml.preferences, false)
    }

}
