package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.DefaultStopServiceEntity
import io.reactivex.Maybe

@Dao
interface DefaultStopServiceDao : BaseDao<DefaultStopServiceEntity> {

    @Query("SELECT * FROM default_stop_services WHERE id = :id")
    fun select(id: String): Maybe<DefaultStopServiceEntity>

    @Query("DELETE FROM default_stop_services")
    fun deleteAll()

}
