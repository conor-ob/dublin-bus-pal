package ie.dublinbuspal.android.data.local.dao;


import androidx.room.Dao;
import androidx.room.Query;
import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.entity.BusStopService;

import java.util.List;

@Dao
public interface BusStopServiceDao extends BaseDao<BusStopService> {

    @Query(DbInfo.SELECT_BUS_STOP_SERVICE)
    BusStopService select(String stopId);

    @Query(DbInfo.SELECT_BUS_STOP_SERVICES)
    List<BusStopService> selectAll();

    @Query(DbInfo.DELETE_BUS_STOP_SERVICES)
    void deleteAll();

}
