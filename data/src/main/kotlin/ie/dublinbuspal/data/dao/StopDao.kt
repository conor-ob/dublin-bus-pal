package ie.dublinbuspal.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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
