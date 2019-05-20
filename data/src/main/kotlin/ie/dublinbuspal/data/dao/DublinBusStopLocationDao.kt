package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.DublinBusStopLocationEntity
import io.reactivex.Maybe

@Dao
interface DublinBusStopLocationDao : BaseDao<DublinBusStopLocationEntity> {

    @Query("SELECT * FROM stop_locations")
    fun selectAll(): Maybe<List<DublinBusStopLocationEntity>>

    @Query("DELETE FROM stop_locations")
    fun deleteAll()

}
