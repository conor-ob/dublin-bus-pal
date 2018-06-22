package ie.dublinbuspal.android.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.entity.BusStop;

import java.util.List;

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
