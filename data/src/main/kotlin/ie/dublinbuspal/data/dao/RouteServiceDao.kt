package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.RouteServiceEntity
import io.reactivex.Maybe

@Dao
interface RouteServiceDao : BaseDao<RouteServiceEntity> {

    @Query("SELECT * FROM route_services WHERE id = :id")
    fun select(id: String): Maybe<RouteServiceEntity>

    @Query("DELETE FROM route_services")
    fun deleteAll()

}
