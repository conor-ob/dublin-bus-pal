package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.dublinbuspal.data.entity.StopEntity
import io.reactivex.Maybe

@Dao
interface StopDao {

    @Query("SELECT * FROM stops")
    fun selectAll(): Maybe<List<StopEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<StopEntity>)

    @Query("DELETE FROM stops")
    fun deleteAll()

}
