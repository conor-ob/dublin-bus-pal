package ie.dublinbuspal.database.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.*
import ie.dublinbuspal.database.DatabaseTxRunner
import ie.dublinbuspal.database.DublinBusDatabase
import ie.dublinbuspal.database.migration.Migrations
import javax.inject.Singleton

@Module
class InMemoryDatabaseModule {

    @Provides
    @Singleton
    fun database(context: Context): DublinBusDatabase = Room
            .inMemoryDatabaseBuilder(context, DublinBusDatabase::class.java)
            .addMigrations(
                    Migrations.MIGRATION_1_2
            )
            .build()

    @Provides
    @Singleton
    fun stopDao(database: DublinBusDatabase): StopDao = database.stopDao()

    @Provides
    @Singleton
    fun routeDao(database: DublinBusDatabase): RouteDao = database.routeDao()

    @Provides
    @Singleton
    fun stopServiceDao(database: DublinBusDatabase): StopServiceDao = database.stopServiceDao()

    @Provides
    @Singleton
    fun routeServiceDao(database: DublinBusDatabase): RouteServiceDao = database.routeServiceDao()

    @Provides
    @Singleton
    fun favouriteStopDao(database: DublinBusDatabase): FavouriteStopDao = database.favouriteStopDao()

    @Provides
    @Singleton
    fun smartDublinStopServiceDao(database: DublinBusDatabase): SmartDublinStopServiceDao = database.smartDublinStopServiceDao()

    @Provides
    @Singleton
    fun detailedStopDao(database: DublinBusDatabase): DetailedStopDao = database.detailedStopDao()

    @Provides
    @Singleton
    fun txRunner(database: DublinBusDatabase): TxRunner = DatabaseTxRunner(database)

}
