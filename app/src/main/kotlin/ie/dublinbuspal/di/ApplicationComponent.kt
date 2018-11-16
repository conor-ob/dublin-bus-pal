package ie.dublinbuspal.di

import dagger.Component
import ie.dublinbuspal.view.nearby.NearbyPresenter
import ie.dublinbuspal.view.search.SearchPresenter
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
//
//    fun liveDataPresenter(): LiveDataPresenter
//
    fun searchPresenter(): SearchPresenter
//
//    fun routePresenter(): RouteServicePresenter
//
//    fun favouritesPresenter(): FavouritesPresenter
//
//    fun rssNewsPresenter(): RssNewsPresenter

}
