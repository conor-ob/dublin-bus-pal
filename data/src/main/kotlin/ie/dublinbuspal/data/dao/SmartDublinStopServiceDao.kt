package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.dublinbuspal.data.entity.SmartDublinStopServiceEntity
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
