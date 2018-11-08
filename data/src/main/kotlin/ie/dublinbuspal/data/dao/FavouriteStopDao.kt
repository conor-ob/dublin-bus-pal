package ie.dublinbuspal.data.dao

import android.arch.persistence.room.*
import ie.dublinbuspal.data.entity.FavouriteStopEntity
import io.reactivex.Maybe

@Dao
interface FavouriteStopDao {

    @Query("SELECT * FROM favourites WHERE id = :id")
    fun select(id: String): Maybe<FavouriteStopEntity>

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
