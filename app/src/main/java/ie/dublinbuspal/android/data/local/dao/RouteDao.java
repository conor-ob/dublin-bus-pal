package ie.dublinbuspal.android.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.entity.Route;

import java.util.List;

@Dao
public abstract class RouteDao implements BaseDao<Route> {

    @Query(DbInfo.SELECT_ROUTES)
    public abstract List<Route> selectAll();

    @Query(DbInfo.DELETE_ROUTES)
    public abstract void deleteAll();

    @Transaction
    public void replaceAll(List<Route> routes) {
        deleteAll();
        insertAll(routes);
    }

}
