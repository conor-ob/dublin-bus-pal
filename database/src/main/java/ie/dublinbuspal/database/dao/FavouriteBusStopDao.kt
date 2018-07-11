package ie.dublinbuspal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ie.dublinbuspal.database.entity.FavouriteBusStopEntity
import io.reactivex.Maybe

@Dao
interface FavouriteBusStopDao {

    @Query("SELECT * FROM favourites")
    fun selectAll(): Maybe<List<FavouriteBusStopEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<FavouriteBusStopEntity>)

    @Query("DELETE FROM favourites")
    fun deleteAll()

}
