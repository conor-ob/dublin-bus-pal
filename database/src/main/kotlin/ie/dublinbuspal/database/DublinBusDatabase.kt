package ie.dublinbuspal.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ie.dublinbuspal.data.Converters
import ie.dublinbuspal.data.dao.*
import ie.dublinbuspal.data.entity.*

@Database(
        version = 2,
        exportSchema = true,
        entities = [
            StopEntity::class,
            RouteEntity::class,
            StopServiceEntity::class,
            RouteServiceEntity::class,
            FavouriteStopEntity::class,
            SmartDublinStopServiceEntity::class
        ]
)
@TypeConverters(Converters::class)
abstract class DublinBusDatabase : RoomDatabase() {

    abstract fun stopDao(): StopDao

    abstract fun routeDao(): RouteDao

    abstract fun stopServiceDao(): StopServiceDao

    abstract fun routeServiceDao(): RouteServiceDao

    abstract fun favouriteStopDao(): FavouriteStopDao

    abstract fun smartDublinStopServiceDao(): SmartDublinStopServiceDao

    abstract fun detailedStopDao(): DetailedStopDao

}