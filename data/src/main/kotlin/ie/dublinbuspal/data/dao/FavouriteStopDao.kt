package ie.dublinbuspal.data.dao

import androidx.room.Dao
import androidx.room.Query
import ie.dublinbuspal.data.entity.FavouriteStopEntity
import io.reactivex.Maybe

@Dao
interface FavouriteStopDao : BaseDao<FavouriteStopEntity> {

    @Query("SELECT * FROM favourites WHERE id = :id")
    fun select(id: String): Maybe<FavouriteStopEntity>

    @Query("SELECT * FROM favourites ORDER BY `order`")
    fun selectAll(): Maybe<List<FavouriteStopEntity>>

    @Query("DELETE FROM favourites")
    fun deleteAll()

    @Query("DELETE FROM favourites WHERE id = :id")
    fun delete(id: String)

    @Query("SELECT COUNT(*) FROM favourites")
    fun count(): Maybe<Long>

}
