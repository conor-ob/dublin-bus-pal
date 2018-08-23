package ie.dublinbuspal.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ie.dublinbuspal.data.entity.RouteServiceEntity
import io.reactivex.Maybe

@Dao
interface RouteServiceDao {

    @Query("SELECT * FROM route_services WHERE id = :id")
    fun select(id: String): Maybe<RouteServiceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RouteServiceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<RouteServiceEntity>)

    @Query("DELETE FROM route_services")
    fun deleteAll()

}
