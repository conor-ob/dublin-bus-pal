package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.GoAheadDublinStopEntity
import io.reactivex.Maybe

@Dao
interface GoAheadDublinStopDao : BaseDao<GoAheadDublinStopEntity> {

    @Query("SELECT * FROM go_ahead_dublin_stops")
    fun selectAll(): Maybe<List<GoAheadDublinStopEntity>>

    @Query("DELETE FROM go_ahead_dublin_stops")
    fun deleteAll()

}
