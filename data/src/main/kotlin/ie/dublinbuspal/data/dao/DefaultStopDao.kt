package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.DefaultStopEntity
import io.reactivex.Maybe

@Dao
interface DefaultStopDao : BaseDao<DefaultStopEntity> {

    @Query("SELECT * FROM default_stops")
    fun selectAll(): Maybe<List<DefaultStopEntity>>

    @Query("DELETE FROM default_stops")
    fun deleteAll()

}
