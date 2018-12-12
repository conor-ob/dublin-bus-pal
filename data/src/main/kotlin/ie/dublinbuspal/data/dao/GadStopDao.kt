package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.GadStopEntity
import io.reactivex.Maybe

@Dao
interface GadStopDao : BaseDao<GadStopEntity> {

    @Query("SELECT * FROM gad_stops")
    fun selectAll(): Maybe<List<GadStopEntity>>

    @Query("DELETE FROM gad_stops")
    fun deleteAll()

}
