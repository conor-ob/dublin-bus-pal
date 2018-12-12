package ie.dublinbuspal.android.data.local.dao;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.entity.BusStop;

@Dao
public abstract class BusStopDao implements BaseDao<BusStop> {

    @Query(DbInfo.SELECT_BUS_STOPS)
    public abstract List<BusStop> selectAll();

    @Query(DbInfo.DELETE_BUS_STOPS)
    public abstract void deleteAll();

    @Transaction
    public void replaceAll(List<BusStop> busStops) {
        deleteAll();
        insertAll(busStops);
    }

}
