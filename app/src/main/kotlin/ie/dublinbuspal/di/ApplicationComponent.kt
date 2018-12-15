package ie.dublinbuspal.di

import dagger.Component
import ie.dublinbuspal.android.view.favourite.FavouritesPresenterImpl
import ie.dublinbuspal.android.view.realtime.RealTimePresenterImpl
import ie.dublinbuspal.android.view.search.SearchPresenterImpl
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

//    fun nearbyPresenter(): NearbyPresenter

    fun liveDataPresenter(): RealTimePresenterImpl

    fun searchPresenter(): SearchPresenterImpl

//    fun routePresenter(): RouteServicePresenter

    fun favouritesPresenter(): FavouritesPresenterImpl

//    fun rssNewsPresenter(): RssNewsPresenter

}
