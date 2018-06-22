package ie.dublinbuspal.android.data.local;

import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.FavouriteBusStop;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;
import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;

import java.util.List;

public interface LocalDataSource {

    List<UncheckedBusStopService> getUncheckedBusStopServices();

    FavouriteBusStop selectFavourite(String stopId);

    boolean deleteFavourite(FavouriteBusStop favourite);

    List<DetailedBusStop> getFavourites();

    List<DetailedBusStop> getDetailedBusStops();

    List<BusStop> getBusStops() throws Exception;

    List<Route> getRoutes() throws Exception;

    RouteService getRouteService(String routeId) throws Exception;

    BusStopService getBusStopService(String stopId) throws Exception;

    boolean insertFavourite(FavouriteBusStop favouriteBusStop);

    void insertBusStopService(BusStopService busStopService);

    void insertRouteService(RouteService routeService);

    void replaceBusStops(List<BusStop> busStops);

    void replaceRoutes(List<Route> routes);

    void replaceBusStopServices(List<UncheckedBusStopService> busStopServices);

}
