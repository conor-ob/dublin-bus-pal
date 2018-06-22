package ie.dublinbuspal.android;

import android.app.Application;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import ie.dublinbuspal.android.di.ApplicationComponent;
import ie.dublinbuspal.android.di.ApplicationModule;
import ie.dublinbuspal.android.di.DaggerApplicationComponent;
import ie.dublinbuspal.android.util.ErrorLog;
import ie.dublinbuspal.android.util.MetaDataUtilities;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import io.fabric.sdk.android.Fabric;
import io.reactivex.plugins.RxJavaPlugins;

public class DublinBusApplication extends Application {

    static {
        RxJavaPlugins.setErrorHandler(ErrorLog::u);
    }

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDagger();
        setupTwitter();
        setupAnalytics();
        setupPreferences();
    }

    private void setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    private void setupTwitter() {
        TwitterConfig config = new TwitterConfig.Builder(getApplicationContext())
                .twitterAuthConfig(new TwitterAuthConfig(
                        MetaDataUtilities.getMetadata(getApplicationContext(),
                                "com.twitter.sdk.android.CONSUMER_KEY"),
                        MetaDataUtilities.getMetadata(getApplicationContext(),
                                "com.twitter.sdk.android.CONSUMER_SECRET")))
                .build();
        Twitter.initialize(config);
    }

    private void setupAnalytics() {
        Fabric.with(getApplicationContext(), new Crashlytics(), new Answers());
    }

    private void setupPreferences() {
        PreferenceManager.setDefaultValues(getApplicationContext(),
                ie.dublinbuspal.android.R.xml.preferences, false);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
