package ie.dublinbuspal.android.data.memory;

import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;

import java.util.List;

public interface CacheDataSource {

    void cacheDetailedBusStops(List<DetailedBusStop> detailedBusStops);

    void cacheRoutes(List<Route> routes);

    void cacheFavourites(List<DetailedBusStop> favourites);

    void cacheBusStopService(BusStopService busStopService);

    void cacheRouteService(RouteService routeService);

    List<DetailedBusStop> getDetailedBusStops();

    List<Route> getRoutes();

    List<DetailedBusStop> getFavourites();

    DetailedBusStop getDetailedBusStop(String stopId);

    BusStopService getBusStopService(String stopId);

    RouteService getRouteService(String routeId);

    void invalidateDetailedBusStops();

    void invalidateFavourites();

    void invalidate();

}
