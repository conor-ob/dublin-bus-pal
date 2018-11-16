package ie.dublinbuspal.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.prefs.DefaultPreferencesRepository
import ie.dublinbuspal.repository.PreferencesRepository
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context

    @Provides
    @Singleton
    fun preferences(): PreferencesRepository = DefaultPreferencesRepository(context)

}
