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
            StopEntity::class,
            RouteEntity::class,
            StopServiceEntity::class,
            RouteServiceEntity::class,
            FavouriteStopEntity::class
        ]
)
@TypeConverters(Converters::class)
abstract class DublinBusDatabase : RoomDatabase() {

    abstract fun stopDao(): StopDao

    abstract fun routeDao(): RouteDao

    abstract fun stopServiceDao(): StopServiceDao

    abstract fun routeServiceDao(): RouteServiceDao

    abstract fun favouriteStopDao(): FavouriteStopDao

}
