package ie.dublinbuspal.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.android.location.LocationProviderImpl
import ie.dublinbuspal.location.LocationProvider
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
    fun locationProvider(): LocationProvider = LocationProviderImpl(context)

}
