package ie.dublinbuspal.android

import android.app.Application
import android.preference.PreferenceManager
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.jakewharton.threetenabp.AndroidThreeTen
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import ie.dublinbuspal.android.util.CrashlyticsTree
import ie.dublinbuspal.android.util.MetadataUtils
import ie.dublinbuspal.di.*
import io.fabric.sdk.android.Fabric
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class DublinBusApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    init {
        RxJavaPlugins.setErrorHandler { Timber.e(it) }
    }

    override fun onCreate() {
        super.onCreate()
        setupAnalytics()
        setupTimber()
        setupThreeTen()
        setupDagger()
        setupTwitter()
        setupPreferences()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    private fun setupThreeTen() {
        AndroidThreeTen.init(applicationContext)
    }

    private fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .databaseModule(DatabaseModule(
                    databaseName = resources.getString(R.string.dublin_bus_database_name)
                ))
                .networkModule(NetworkModule(
                    dublinBusApiEndpoint = resources.getString(R.string.dublin_bus_soap_api_endpoint),
                    rtpiApiEndpoint = resources.getString(R.string.dublin_bus_rest_api_endpoint),
                    rssApiEndpoint = resources.getString(R.string.dublin_bus_rss_api_endpoint)
                ))
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
        if (!BuildConfig.DEBUG) {
            Fabric.with(applicationContext, Crashlytics(), Answers())
        }
    }

    private fun setupPreferences() {
        PreferenceManager.setDefaultValues(applicationContext, ie.dublinbuspal.android.R.xml.preferences, false)
    }

}
