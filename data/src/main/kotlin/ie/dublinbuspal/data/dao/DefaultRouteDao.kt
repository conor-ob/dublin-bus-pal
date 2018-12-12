package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.RouteEntity
import io.reactivex.Maybe

@Dao
interface DefaultRouteDao : BaseDao<RouteEntity> {

    @Query("SELECT * FROM routes")
    fun selectAll(): Maybe<List<RouteEntity>>

    @Query("DELETE FROM routes")
    fun deleteAll()

}
