package ie.dublinbuspal.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.android.location.LocationProviderImpl
import ie.dublinbuspal.android.util.DownloadProgressListenerImpl
import ie.dublinbuspal.android.util.InternetManagerImpl
import ie.dublinbuspal.android.view.settings.DefaultPreferenceStore
import ie.dublinbuspal.android.view.settings.ThemeRepository
import ie.dublinbuspal.location.LocationProvider
import ie.dublinbuspal.util.DownloadProgressListener
import ie.dublinbuspal.util.InternetManager
import ie.dublinbuspal.util.PreferenceStore
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

    @Provides
    @Singleton
    fun downloadProgressListener(): DownloadProgressListener = DownloadProgressListenerImpl()

    @Provides
    @Singleton
    fun preferenceStore(context: Context): PreferenceStore = DefaultPreferenceStore(context)

    @Provides
    @Singleton
    fun themeRepository(
        context: Context,
        preferenceStore: PreferenceStore
    ): ThemeRepository = ThemeRepository(context.resources, preferenceStore)

}
