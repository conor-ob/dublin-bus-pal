package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.BacStopEntity
import io.reactivex.Maybe

@Dao
interface BacStopDao : BaseDao<BacStopEntity> {

    @Query("SELECT * FROM bac_stops")
    fun selectAll(): Maybe<List<BacStopEntity>>

    @Query("DELETE FROM bac_stops")
    fun deleteAll()

}
