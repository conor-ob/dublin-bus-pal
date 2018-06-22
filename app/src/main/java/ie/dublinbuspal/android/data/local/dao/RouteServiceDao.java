package ie.dublinbuspal.android.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.entity.RouteService;

@Dao
public interface RouteServiceDao extends BaseDao<RouteService> {

    @Query(DbInfo.SELECT_ROUTE_SERVICE)
    RouteService select(String routeId);

    @Query(DbInfo.DELETE_ROUTE_SERVICES)
    void deleteAll();

}
