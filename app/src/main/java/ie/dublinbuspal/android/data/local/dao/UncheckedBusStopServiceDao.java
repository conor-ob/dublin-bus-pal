package ie.dublinbuspal.android.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;

import java.util.List;

@Dao
public abstract class UncheckedBusStopServiceDao implements BaseDao<UncheckedBusStopService> {

    @Query(DbInfo.SELECT_UNCHECKED_BUS_STOP_SERVICES)
    public abstract List<UncheckedBusStopService> selectAll();

    @Query(DbInfo.DELETE_UNCHECKED_BUS_STOP_SERVICES)
    public abstract void deleteAll();

    @Transaction
    public void replaceAll(List<UncheckedBusStopService> busStopServices) {
        deleteAll();
        insertAll(busStopServices);
    }

}
