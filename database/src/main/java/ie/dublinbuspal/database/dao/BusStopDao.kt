package ie.dublinbuspal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ie.dublinbuspal.database.entity.BusStopEntity
import io.reactivex.Maybe

@Dao
interface BusStopDao {

    @Query("SELECT * FROM stops")
    fun selectAll(): Maybe<List<BusStopEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<BusStopEntity>)

    @Query("DELETE FROM stops")
    fun deleteAll()

}
