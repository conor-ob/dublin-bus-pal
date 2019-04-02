package ie.dublinbuspal.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.android.location.LocationProviderImpl
import ie.dublinbuspal.android.util.InternetManagerImpl
import ie.dublinbuspal.location.LocationProvider
import ie.dublinbuspal.util.InternetManager
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context

//    @Provides
//    @Singleton
//    fun preferences(): PreferencesRepository = DefaultPreferencesRepository(context)

    @Provides
    @Singleton
    fun locationProvider(context: Context): LocationProvider = LocationProviderImpl(context)

    @Provides
    @Singleton
    fun internetManager(context: Context): InternetManager = InternetManagerImpl(context)

}
