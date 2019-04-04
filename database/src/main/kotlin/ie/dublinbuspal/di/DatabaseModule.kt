package ie.dublinbuspal.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DefaultRouteDao
import ie.dublinbuspal.data.dao.FavouriteStopDao
import ie.dublinbuspal.data.dao.GoAheadDublinRouteDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.resource.DublinBusStopCacheResource
import ie.dublinbuspal.database.DatabaseTxRunner
import ie.dublinbuspal.database.DublinBusDatabase
import ie.dublinbuspal.database.migration.Migrations.MIGRATION_3_4
import ie.dublinbuspal.database.resource.DublinBusStopCacheResourceImpl
import javax.inject.Singleton

@Module
class DatabaseModule(
        private val databaseName: String
) {

    @Provides
    @Singleton
    fun database(context: Context): DublinBusDatabase = Room
            .databaseBuilder(context, DublinBusDatabase::class.java, databaseName)
            .fallbackToDestructiveMigrationFrom(1, 2)
            .addMigrations(
                    MIGRATION_3_4
            )
            .build()

    @Provides
    @Singleton
    fun dublinBusStopCacheResource(database: DublinBusDatabase, txRunner: TxRunner): DublinBusStopCacheResource {
        val dublinBusStopLocationDao = database.dublinBusStopLocationDao()
        val dublinBusStopServiceDao = database.dublinBusStopServiceDao()
        val dublinBusStopDao = database.dublinBusStopDao()
        return DublinBusStopCacheResourceImpl(dublinBusStopLocationDao, dublinBusStopServiceDao, dublinBusStopDao, txRunner)
    }

    @Provides
    @Singleton
    fun defaultRouteDao(database: DublinBusDatabase): DefaultRouteDao = database.defaultRouteDao()

    @Provides
    @Singleton
    fun goAheadDublinRouteDao(database: DublinBusDatabase): GoAheadDublinRouteDao = database.goAheadDublinRouteDao()

    @Provides
    @Singleton
    fun favouriteStopDao(database: DublinBusDatabase): FavouriteStopDao = database.favouriteStopDao()

    @Provides
    @Singleton
    fun persisterDao(database: DublinBusDatabase): PersisterDao = database.persisterDao()

    @Provides
    @Singleton
    fun txRunner(database: DublinBusDatabase): TxRunner = DatabaseTxRunner(database)

}
