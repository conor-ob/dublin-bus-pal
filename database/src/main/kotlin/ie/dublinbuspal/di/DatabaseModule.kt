package ie.dublinbuspal.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.*
import ie.dublinbuspal.database.DatabaseTxRunner
import ie.dublinbuspal.database.DublinBusDatabase
import ie.dublinbuspal.database.migration.Migrations.MIGRATION_1_2
import javax.inject.Singleton

@Module
class DatabaseModule(private val databaseName: String) {

    @Provides
    @Singleton
    fun database(context: Context): DublinBusDatabase = Room
            .databaseBuilder(context, DublinBusDatabase::class.java, databaseName)
            .addMigrations(
                    MIGRATION_1_2
            )
            .build()

    @Provides
    @Singleton
    fun defaultStopDao(database: DublinBusDatabase): DefaultStopDao = database.defaultStopDao()

    @Provides
    @Singleton
    fun dublinBusStopDao(database: DublinBusDatabase): DublinBusStopDao = database.dublinBusStopDao()

    @Provides
    @Singleton
    fun goAheadDublinStopDao(database: DublinBusDatabase): GoAheadDublinStopDao = database.goAheadDublinStopDao()

    @Provides
    @Singleton
    fun defaultRouteDao(database: DublinBusDatabase): DefaultRouteDao = database.defaultRouteDao()

    @Provides
    @Singleton
    fun defaultStopServiceDao(database: DublinBusDatabase): DefaultStopServiceDao = database.defaultStopServiceDao()

    @Provides
    @Singleton
    fun defaultRouteServiceDao(database: DublinBusDatabase): DefaultRouteServiceDao = database.defaultRouteServiceDao()

    @Provides
    @Singleton
    fun favouriteStopDao(database: DublinBusDatabase): FavouriteStopDao = database.favouriteStopDao()

    @Provides
    @Singleton
    fun txRunner(database: DublinBusDatabase): TxRunner = DatabaseTxRunner(database)

}
