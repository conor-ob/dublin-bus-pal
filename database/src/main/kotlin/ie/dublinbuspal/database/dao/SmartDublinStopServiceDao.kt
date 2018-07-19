package ie.dublinbuspal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ie.dublinbuspal.database.entity.SmartDublinStopServiceEntity
import io.reactivex.Maybe

@Dao
interface SmartDublinStopServiceDao {

    @Query("SELECT * FROM smart_dublin_stop_services")
    fun selectAll(): Maybe<List<SmartDublinStopServiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<SmartDublinStopServiceEntity>)

    @Query("DELETE FROM smart_dublin_stop_services")
    fun deleteAll()

}
