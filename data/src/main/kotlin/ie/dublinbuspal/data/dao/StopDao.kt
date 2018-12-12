package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.StopEntity
import io.reactivex.Maybe

@Dao
interface StopDao : BaseDao<StopEntity> {

    @Query("SELECT * FROM stops")
    fun selectAll(): Maybe<List<StopEntity>>

    @Query("DELETE FROM stops")
    fun deleteAll()

}
