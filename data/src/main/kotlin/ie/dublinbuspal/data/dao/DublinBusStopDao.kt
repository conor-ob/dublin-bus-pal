package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.DublinBusStopEntity
import io.reactivex.Maybe

@Dao
interface DublinBusStopDao : BaseDao<DublinBusStopEntity> {

    @Query("SELECT * FROM dublin_bus_stops")
    fun selectAll(): Maybe<List<DublinBusStopEntity>>

    @Query("DELETE FROM dublin_bus_stops")
    fun deleteAll()

}
