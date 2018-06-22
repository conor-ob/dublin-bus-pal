package ie.dublinbuspal.android

import android.app.Application
import android.preference.PreferenceManager
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import ie.dublinbuspal.android.di.ApplicationComponent
import ie.dublinbuspal.android.di.ApplicationModule
import ie.dublinbuspal.android.di.DaggerApplicationComponent
import ie.dublinbuspal.android.util.ErrorLog
import ie.dublinbuspal.android.util.MetaDataUtilities
import io.fabric.sdk.android.Fabric
import io.reactivex.plugins.RxJavaPlugins

class DublinBusApplication : Application() {

    companion object {
        init {
            RxJavaPlugins.setErrorHandler(ErrorLog::u)
        }
    }

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        setupTwitter()
        setupAnalytics()
        setupPreferences()
    }

    private fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }

    private fun setupTwitter() {
        val config = TwitterConfig.Builder(applicationContext)
                .twitterAuthConfig(TwitterAuthConfig(
                        MetaDataUtilities.getMetadata(applicationContext,
                                "com.twitter.sdk.android.CONSUMER_KEY"),
                        MetaDataUtilities.getMetadata(applicationContext,
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
        PreferenceManager.setDefaultValues(applicationContext,
                ie.dublinbuspal.android.R.xml.preferences, false)
    }

    fun getApplicationComponent(): ApplicationComponent? {
        return applicationComponent
    }

}
