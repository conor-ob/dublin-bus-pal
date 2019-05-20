package ie.dublinbuspal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.dublinbuspal.data.Converters
import ie.dublinbuspal.data.dao.*
import ie.dublinbuspal.data.entity.*

@Database(
        version = 5,
        exportSchema = true,
        entities = [
            DublinBusStopLocationEntity::class,
            DublinBusStopServiceEntity::class,
            DublinBusRouteInfoEntity::class,
            DublinBusRouteVariantEntity::class,
            FavouriteStopEntity::class,
            PersisterEntity::class
        ]
)
@TypeConverters(Converters::class)
abstract class DublinBusDatabase : RoomDatabase() {

    abstract fun dublinBusStopDao(): DublinBusStopDao

    abstract fun dublinBusStopLocationDao(): DublinBusStopLocationDao

    abstract fun dublinBusStopServiceDao(): DublinBusStopServiceDao

    abstract fun dublinBusRouteDao(): DublinBusRouteDao

    abstract fun dublinBusRouteInfoDao(): DublinBusRouteInfoDao

    abstract fun dublinBusRouteVariantDao(): DublinBusRouteVariantDao

    abstract fun favouriteStopDao(): FavouriteStopDao

    abstract fun persisterDao(): PersisterDao

}
