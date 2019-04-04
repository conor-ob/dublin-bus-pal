package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ie.dublinbuspal.data.entity.DublinBusRouteEntity
import io.reactivex.Maybe

@Dao
interface DublinBusRouteDao {

    @Transaction
    @Query("SELECT * FROM routes")
    fun selectAll(): Maybe<List<DublinBusRouteEntity>>

}
