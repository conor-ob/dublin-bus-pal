package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.dublinbuspal.data.entity.StopServiceEntity
import io.reactivex.Maybe

@Dao
interface StopServiceDao {

    @Query("SELECT * FROM stop_services WHERE id = :id")
    fun select(id: String): Maybe<StopServiceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: StopServiceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<StopServiceEntity>)

    @Query("DELETE FROM stop_services")
    fun deleteAll()

}
