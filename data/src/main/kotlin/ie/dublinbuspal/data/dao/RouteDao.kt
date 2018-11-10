package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.dublinbuspal.data.entity.RouteEntity
import io.reactivex.Maybe

@Dao
interface RouteDao {

    @Query("SELECT * FROM routes")
    fun selectAll(): Maybe<List<RouteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<RouteEntity>)

    @Query("DELETE FROM routes")
    fun deleteAll()

}
