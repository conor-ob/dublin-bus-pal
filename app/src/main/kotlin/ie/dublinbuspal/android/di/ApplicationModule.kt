package ie.dublinbuspal.android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.android.preferences.DefaultPreferencesRepository
import ie.dublinbuspal.base.PreferencesRepository
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