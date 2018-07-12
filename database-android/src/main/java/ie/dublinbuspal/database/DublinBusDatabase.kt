package ie.dublinbuspal.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ie.dublinbuspal.database.dao.*
import ie.dublinbuspal.database.entity.*

@Database(
        version = 2,
        exportSchema = true,
        entities = [
            BusStopEntity::class,
            RouteEntity::class,
            BusStopServiceEntity::class,
            RouteServiceEntity::class,
            FavouriteBusStopEntity::class
        ]
)
@TypeConverters(Converters::class)
abstract class DublinBusDatabase : RoomDatabase() {

    abstract fun busStopDao(): BusStopDao

    abstract fun routeDao(): RouteDao

    abstract fun busStopServiceDao(): BusStopServiceDao

    abstract fun routeServiceDao(): RouteServiceDao

    abstract fun favouriteBusStopDao(): FavouriteBusStopDao

}
