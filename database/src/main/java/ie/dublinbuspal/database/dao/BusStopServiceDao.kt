package ie.dublinbuspal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ie.dublinbuspal.database.entity.BusStopServiceEntity
import io.reactivex.Maybe

@Dao
interface BusStopServiceDao {

    @Query("SELECT * FROM stop_services")
    fun selectAll(): Maybe<List<BusStopServiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<BusStopServiceEntity>)

    @Query("DELETE FROM stop_services")
    fun deleteAll()

}
