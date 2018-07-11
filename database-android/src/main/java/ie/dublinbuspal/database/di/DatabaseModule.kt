package ie.dublinbuspal.database.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.DatabaseTxRunner
import ie.dublinbuspal.database.DublinBusDatabase
import ie.dublinbuspal.database.migration.Migrations.MIGRATION_1_2
import ie.dublinbuspal.database.R
import ie.dublinbuspal.database.dao.BusStopDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): DublinBusDatabase = Room
            .databaseBuilder(context, DublinBusDatabase::class.java, context.getString(R.string.database_name))
            .addMigrations(
                    MIGRATION_1_2
            )
            .build()

    @Provides
    @Singleton
    fun provideTxRunner(database: DublinBusDatabase): TxRunner = DatabaseTxRunner(database)

    @Provides
    @Singleton
    fun provideBusStopDao(database: DublinBusDatabase): BusStopDao = database.busStopDao()

}
