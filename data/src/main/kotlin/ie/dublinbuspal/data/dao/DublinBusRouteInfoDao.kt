package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.DublinBusRouteInfoEntity
import io.reactivex.Maybe

@Dao
interface DublinBusRouteInfoDao : BaseDao<DublinBusRouteInfoEntity> {

    @Query("SELECT * FROM routes")
    fun selectAll(): Maybe<List<DublinBusRouteInfoEntity>>

    @Query("DELETE FROM routes")
    fun deleteAll()

}
