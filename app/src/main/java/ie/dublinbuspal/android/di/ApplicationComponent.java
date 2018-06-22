package ie.dublinbuspal.android.di;

import ie.dublinbuspal.android.view.favourite.FavouritesFragment;
import ie.dublinbuspal.android.view.nearby.NearbyFragment;
import ie.dublinbuspal.android.view.news.rss.RssFragment;
import ie.dublinbuspal.android.view.news.twitter.TwitterFragment;
import ie.dublinbuspal.android.view.realtime.RealTimeActivity;
import ie.dublinbuspal.android.view.route.RouteActivity;
import ie.dublinbuspal.android.view.search.SearchFragment;
import ie.dublinbuspal.android.view.settings.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, PresenterModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    void inject(TwitterFragment twitterFragment);

    void inject(RssFragment rssFragment);

    void inject(NearbyFragment nearbyFragment);

    void inject(FavouritesFragment favouritesFragment);

    void inject(SearchFragment searchFragment);

    void inject(RealTimeActivity realTimeActivity);

    void inject(RouteActivity routeActivity);

    void inject(SettingsActivity.MainPreferenceFragment mainPreferenceFragment);

}
