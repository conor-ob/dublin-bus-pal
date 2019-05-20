package ie.dublinbuspal.di

import dagger.Component
import ie.dublinbuspal.android.view.favourite.FavouritesPresenterImpl
import ie.dublinbuspal.android.view.nearby.NearbyPresenterImpl
import ie.dublinbuspal.android.view.news.rss.RssPresenterImpl
import ie.dublinbuspal.android.view.news.twitter.TwitterPresenterImpl
import ie.dublinbuspal.android.view.realtime.RealTimePresenterImpl
import ie.dublinbuspal.android.view.routeservice.RouteServicePresenterImpl
import ie.dublinbuspal.android.view.search.SearchPresenterImpl
import ie.dublinbuspal.android.view.settings.SettingsActivity
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ApplicationModule::class,
            NetworkModule::class,
            DatabaseModule::class,
            RepositoryModule::class
        ]
)
interface ApplicationComponent {

    fun nearbyPresenter(): NearbyPresenterImpl

    fun liveDataPresenter(): RealTimePresenterImpl

    fun searchPresenter(): SearchPresenterImpl

    fun routeServicePresenter(): RouteServicePresenterImpl

    fun favouritesPresenter(): FavouritesPresenterImpl

    fun rssNewsPresenter(): RssPresenterImpl

    fun twitterPresenter(): TwitterPresenterImpl

    fun inject(mainPreferenceFragment: SettingsActivity.MainPreferenceFragment)

}
