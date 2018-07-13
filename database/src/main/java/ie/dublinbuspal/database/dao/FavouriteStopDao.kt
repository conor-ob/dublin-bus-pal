package ie.dublinbuspal.database.dao

import android.arch.persistence.room.*
import ie.dublinbuspal.database.entity.FavouriteStopEntity
import io.reactivex.Maybe

@Dao
interface FavouriteStopDao {

    @Query("SELECT * FROM favourites")
    fun selectAll(): Maybe<List<FavouriteStopEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavouriteStopEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: FavouriteStopEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(entities: List<FavouriteStopEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<FavouriteStopEntity>)

    @Query("DELETE FROM favourites")
    fun deleteAll()

}
