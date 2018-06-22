package ie.dublinbuspal.android.data.local;

import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.FavouriteBusStop;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;
import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;

import java.util.List;

public class LocalDataSourceImpl implements LocalDataSource {

    private final DublinBusDatabase dublinBusDatabase;

    public LocalDataSourceImpl(DublinBusDatabase dublinBusDatabase) {
        this.dublinBusDatabase = dublinBusDatabase;
    }

    @Override
    public List<BusStop> getBusStops() {
        return dublinBusDatabase.busStopDao().selectAll();
    }

    @Override
    public List<DetailedBusStop> getFavourites() {
        return dublinBusDatabase.detailedBusStopDao().selectAllFavourites();
    }

    @Override
    public boolean insertFavourite(FavouriteBusStop favouriteBusStop) {
        int beforeCount = dublinBusDatabase.favouriteDao().rowCount();
        dublinBusDatabase.favouriteDao().insert(favouriteBusStop);
        int afterCount = dublinBusDatabase.favouriteDao().rowCount();
        return afterCount == beforeCount + 1;
    }

    @Override
    public BusStopService getBusStopService(String stopId) {
        return dublinBusDatabase.busStopServiceDao().select(stopId);
    }

    @Override
    public void insertBusStopService(BusStopService busStopService) {
        dublinBusDatabase.busStopServiceDao().insert(busStopService);
    }

    @Override
    public List<Route> getRoutes() {
        return dublinBusDatabase.routeDao().selectAll();
    }

    @Override
    public RouteService getRouteService(String routeId) {
        return dublinBusDatabase.routeServiceDao().select(routeId);
    }

    @Override
    public void insertRouteService(RouteService routeService) {
        dublinBusDatabase.routeServiceDao().insert(routeService);
    }

    @Override
    public void replaceBusStops(List<BusStop> busStops) {
        dublinBusDatabase.busStopDao().replaceAll(busStops);
    }

    @Override
    public void replaceRoutes(List<Route> routes) {
        dublinBusDatabase.routeServiceDao().deleteAll();
        dublinBusDatabase.routeDao().replaceAll(routes);
    }

    @Override
    public void replaceBusStopServices(List<UncheckedBusStopService> busStopServices) {
        dublinBusDatabase.busStopServiceDao().deleteAll();
        dublinBusDatabase.uncheckedBusStopServiceDao().replaceAll(busStopServices);
    }

    @Override
    public List<UncheckedBusStopService> getUncheckedBusStopServices() {
        return dublinBusDatabase.uncheckedBusStopServiceDao().selectAll();
    }

    @Override
    public List<DetailedBusStop> getDetailedBusStops() {
        return dublinBusDatabase.detailedBusStopDao().selectAll();
    }

    @Override
    public FavouriteBusStop selectFavourite(String stopId) {
        return dublinBusDatabase.favouriteDao().selectFavourite(stopId);
    }

    @Override
    public boolean deleteFavourite(FavouriteBusStop favourite) {
        int beforeRowCount = dublinBusDatabase.favouriteDao().rowCount();
        dublinBusDatabase.favouriteDao().delete(favourite);
        int afterRowCount = dublinBusDatabase.favouriteDao().rowCount();
        return afterRowCount == beforeRowCount - 1;
    }

}
