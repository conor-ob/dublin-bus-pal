package ie.dublinbuspal.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ie.dublinbuspal.database.dao.BusStopDao
import ie.dublinbuspal.database.entity.BusStopEntity

@Database(
        version = 1,
        exportSchema = true,
        entities = [
            BusStopEntity::class
        ]
)
//@TypeConverters(Converters::class)
abstract class DublinBusDatabase : RoomDatabase() {

    abstract fun busStopDao(): BusStopDao

}
