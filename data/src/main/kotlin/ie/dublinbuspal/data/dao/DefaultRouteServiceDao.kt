package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.DefaultRouteServiceEntity
import io.reactivex.Maybe

@Dao
interface DefaultRouteServiceDao : BaseDao<DefaultRouteServiceEntity> {

    @Query("SELECT * FROM default_route_services WHERE id = :id")
    fun select(id: String): Maybe<DefaultRouteServiceEntity>

    @Query("DELETE FROM default_route_services")
    fun deleteAll()

}
