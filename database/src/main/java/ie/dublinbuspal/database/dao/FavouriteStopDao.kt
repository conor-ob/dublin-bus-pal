package ie.dublinbuspal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ie.dublinbuspal.database.entity.FavouriteStopEntity
import io.reactivex.Maybe

@Dao
interface FavouriteStopDao {

    @Query("SELECT * FROM favourites")
    fun selectAll(): Maybe<List<FavouriteStopEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<FavouriteStopEntity>)

    @Query("DELETE FROM favourites")
    fun deleteAll()

}
