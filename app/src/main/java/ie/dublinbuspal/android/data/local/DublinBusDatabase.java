package ie.dublinbuspal.android.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import ie.dublinbuspal.android.data.local.dao.DetailedBusStopDao;
import ie.dublinbuspal.android.data.local.dao.RouteDao;
import ie.dublinbuspal.android.data.local.dao.RouteServiceDao;
import ie.dublinbuspal.android.data.local.dao.UncheckedBusStopServiceDao;
import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.dao.BusStopDao;
import ie.dublinbuspal.android.data.local.entity.FavouriteBusStop;
import ie.dublinbuspal.android.data.local.dao.BusStopServiceDao;
import ie.dublinbuspal.android.data.local.dao.FavouriteDao;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;
import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;

@Database(version = 1,
        exportSchema = false,
        entities = {
        BusStop.class,
        Route.class,
        BusStopService.class,
        RouteService.class,
        FavouriteBusStop.class,
        UncheckedBusStopService.class})
@TypeConverters({Converters.class})
public abstract class DublinBusDatabase extends RoomDatabase {

    public abstract BusStopDao busStopDao();

    public abstract DetailedBusStopDao detailedBusStopDao();

    public abstract RouteDao routeDao();

    public abstract BusStopServiceDao busStopServiceDao();

    public abstract RouteServiceDao routeServiceDao();

    public abstract FavouriteDao favouriteDao();

    public abstract UncheckedBusStopServiceDao uncheckedBusStopServiceDao();

}
