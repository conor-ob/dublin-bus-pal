package ie.dublinbuspal.android.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;
import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.entity.FavouriteBusStop;

@Dao
public interface FavouriteDao extends BaseDao<FavouriteBusStop> {

    @Query(DbInfo.SELECT_FAVOURITE)
    FavouriteBusStop selectFavourite(String id);

    @Query(DbInfo.COUNT_FAVOURITES)
    int rowCount();

}
