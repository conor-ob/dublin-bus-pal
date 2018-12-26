package ie.dublinbuspal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.dublinbuspal.data.Converters
import ie.dublinbuspal.data.dao.*
import ie.dublinbuspal.data.entity.*

@Database(
        version = 2,
        exportSchema = true,
        entities = [
            DefaultStopEntity::class,
            DublinBusStopEntity::class,
            GoAheadDublinStopEntity::class,
            DefaultRouteEntity::class,
            GoAheadDublinRouteEntity::class,
            FavouriteStopEntity::class,
            PersisterEntity::class
        ]
)
@TypeConverters(Converters::class)
abstract class DublinBusDatabase : RoomDatabase() {

    abstract fun defaultStopDao(): DefaultStopDao

    abstract fun dublinBusStopDao(): DublinBusStopDao

    abstract fun goAheadDublinStopDao(): GoAheadDublinStopDao

    abstract fun defaultRouteDao(): DefaultRouteDao

    abstract fun goAheadDublinRouteDao(): GoAheadDublinRouteDao

    abstract fun favouriteStopDao(): FavouriteStopDao

    abstract fun persisterDao(): PersisterDao

}
