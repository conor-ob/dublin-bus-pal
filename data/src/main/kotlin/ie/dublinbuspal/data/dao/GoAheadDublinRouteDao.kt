package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.GoAheadDublinRouteEntity
import io.reactivex.Maybe

@Dao
interface GoAheadDublinRouteDao : BaseDao<GoAheadDublinRouteEntity> {

    @Query("SELECT * FROM go_ahead_dublin_routes")
    fun selectAll(): Maybe<List<GoAheadDublinRouteEntity>>

    @Query("DELETE FROM go_ahead_dublin_routes")
    fun deleteAll()

}
