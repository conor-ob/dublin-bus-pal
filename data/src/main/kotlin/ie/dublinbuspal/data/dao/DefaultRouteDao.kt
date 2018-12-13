package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.DefaultRouteEntity
import io.reactivex.Maybe

@Dao
interface DefaultRouteDao : BaseDao<DefaultRouteEntity> {

    @Query("SELECT * FROM default_routes")
    fun selectAll(): Maybe<List<DefaultRouteEntity>>

    @Query("DELETE FROM default_routes")
    fun deleteAll()

}
