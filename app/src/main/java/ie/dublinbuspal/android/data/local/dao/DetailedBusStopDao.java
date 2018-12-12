package ie.dublinbuspal.android.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;

import java.util.List;

@Dao
public interface DetailedBusStopDao {

    @Query(DbInfo.SELECT_DETAILED_BUS_STOPS)
    List<DetailedBusStop> selectAll();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query(DbInfo.SELECT_DETAILED_FAVOURITES)
    List<DetailedBusStop> selectAllFavourites();

}
