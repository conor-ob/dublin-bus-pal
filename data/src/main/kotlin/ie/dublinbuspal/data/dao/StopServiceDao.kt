package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.StopServiceEntity
import io.reactivex.Maybe

@Dao
interface StopServiceDao : BaseDao<StopServiceEntity> {

    @Query("SELECT * FROM stop_services WHERE id = :id")
    fun select(id: String): Maybe<StopServiceEntity>

    @Query("DELETE FROM stop_services")
    fun deleteAll()

}
