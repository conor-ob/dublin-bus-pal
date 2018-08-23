package ie.dublinbuspal.android.di

import dagger.Component
import ie.dublinbuspal.android.view.favourites.FavouritesPresenter
import ie.dublinbuspal.android.view.livedata.LiveDataPresenter
import ie.dublinbuspal.android.view.nearby.NearbyPresenter
import ie.dublinbuspal.android.view.news.rss.RssNewsPresenter
import ie.dublinbuspal.android.view.routeservice.RouteServicePresenter
import ie.dublinbuspal.android.view.search.SearchPresenter
import ie.dublinbuspal.di.DatabaseModule
import ie.dublinbuspal.di.NetworkModule
import ie.dublinbuspal.di.RepositoryModule
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

    fun nearbyPresenter(): NearbyPresenter

    fun liveDataPresenter(): LiveDataPresenter

    fun searchPresenter(): SearchPresenter

    fun routePresenter(): RouteServicePresenter

    fun favouritesPresenter(): FavouritesPresenter

    fun rssNewsPresenter(): RssNewsPresenter

}
