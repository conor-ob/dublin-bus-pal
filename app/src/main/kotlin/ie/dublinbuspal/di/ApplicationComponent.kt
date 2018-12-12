package ie.dublinbuspal.di

import dagger.Component
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

//    fun liveDataPresenter(): LiveDataPresenter

    fun searchPresenter(): SearchPresenterImpl

//    fun routePresenter(): RouteServicePresenter

//    fun favouritesPresenter(): FavouritesPresenter

//    fun rssNewsPresenter(): RssNewsPresenter

}
