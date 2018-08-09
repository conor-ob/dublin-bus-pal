package ie.dublinbuspal.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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
