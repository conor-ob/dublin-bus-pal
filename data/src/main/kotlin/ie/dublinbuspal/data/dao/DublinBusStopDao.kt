package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ie.dublinbuspal.data.entity.DublinBusStopEntity
import io.reactivex.Maybe

@Dao
interface DublinBusStopDao {

    @Transaction
    @Query("SELECT * FROM stop_locations")
    fun selectAll(): Maybe<List<DublinBusStopEntity>>

}
