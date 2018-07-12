package ie.dublinbuspal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ie.dublinbuspal.database.entity.StopServiceEntity
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
