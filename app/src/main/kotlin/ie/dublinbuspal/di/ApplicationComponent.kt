package ie.dublinbuspal.di

import dagger.Component
import ie.dublinbuspal.android.view.favourites.FavouritesPresenterImpl
import ie.dublinbuspal.android.view.livedata.LiveDataPresenterImpl
import ie.dublinbuspal.android.view.nearby.NearbyPresenterImpl
import ie.dublinbuspal.android.view.news.rss.RssPresenterImpl
import ie.dublinbuspal.android.view.news.twitter.TwitterPresenterImpl
import ie.dublinbuspal.android.view.route.RoutePresenterImpl
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

    fun liveDataPresenter(): LiveDataPresenterImpl

    fun searchPresenter(): SearchPresenterImpl

    fun routeServicePresenter(): RoutePresenterImpl

    fun favouritesPresenter(): FavouritesPresenterImpl

    fun rssNewsPresenter(): RssPresenterImpl

    fun twitterPresenter(): TwitterPresenterImpl

    fun inject(mainPreferenceFragment: SettingsActivity.MainPreferenceFragment)

}
