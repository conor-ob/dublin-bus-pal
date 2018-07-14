package ie.dublinbuspal.database.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.DatabaseTxRunner
import ie.dublinbuspal.database.DublinBusDatabase
import ie.dublinbuspal.database.R
import ie.dublinbuspal.database.dao.*
import ie.dublinbuspal.database.migration.Migrations.MIGRATION_1_2
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun database(context: Context): DublinBusDatabase = Room
            .databaseBuilder(context, DublinBusDatabase::class.java, context.getString(R.string.database_name))
            .addMigrations(
                    MIGRATION_1_2
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
    fun txRunner(database: DublinBusDatabase): TxRunner = DatabaseTxRunner(database)

}
