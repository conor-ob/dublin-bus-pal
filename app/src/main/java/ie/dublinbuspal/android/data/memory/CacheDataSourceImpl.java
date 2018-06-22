package ie.dublinbuspal.android.data.memory;

import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheDataSourceImpl implements CacheDataSource {

    private final Map<String, Route> cachedRoutes;
    private final Map<String, DetailedBusStop> cachedFavourites;
    private final Map<String, RouteService> cachedRouteServices;
    private final Map<String, BusStopService> cachedBusStopServices;
    private final Map<String, DetailedBusStop> cachedDetailedBusStops;

    public CacheDataSourceImpl() {
        cachedRoutes = new HashMap<>();
        cachedFavourites = new HashMap<>();
        cachedRouteServices = new HashMap<>();
        cachedBusStopServices = new HashMap<>();
        cachedDetailedBusStops = new HashMap<>();
    }

    @Override
    public void cacheDetailedBusStops(List<DetailedBusStop> detailedBusStops) {
        cachedDetailedBusStops.clear();
        for (DetailedBusStop detailedBusStop : detailedBusStops) {
            cachedDetailedBusStops.put(detailedBusStop.getId(), detailedBusStop);
        }
    }

    @Override
    public void cacheRoutes(List<Route> routes) {
        cachedRoutes.clear();
        for (Route route : routes) {
            cachedRoutes.put(route.getRouteId(), route);
        }
    }

    @Override
    public void cacheFavourites(List<DetailedBusStop> favourites) {
        cachedFavourites.clear();
        for (DetailedBusStop favourite : favourites) {
            cachedFavourites.put(favourite.getId(), favourite);
        }
    }

    @Override
    public void cacheBusStopService(BusStopService busStopService) {
        cachedBusStopServices.put(busStopService.getStopId(), busStopService);
    }

    @Override
    public void cacheRouteService(RouteService routeService) {
        cachedRouteServices.put(routeService.getRouteId(), routeService);
    }

    @Override
    public List<DetailedBusStop> getDetailedBusStops() {
        return new ArrayList<>(cachedDetailedBusStops.values());
    }

    @Override
    public List<Route> getRoutes() {
        return new ArrayList<>(cachedRoutes.values());
    }

    @Override
    public List<DetailedBusStop> getFavourites() {
        return new ArrayList<>(cachedFavourites.values());
    }

    @Override
    public DetailedBusStop getDetailedBusStop(String stopId) {
        return cachedDetailedBusStops.get(stopId);
    }

    @Override
    public BusStopService getBusStopService(String stopId) {
        return cachedBusStopServices.get(stopId);
    }

    @Override
    public RouteService getRouteService(String routeId) {
        return cachedRouteServices.get(routeId);
    }

    @Override
    public void invalidateDetailedBusStops() {
        cachedDetailedBusStops.clear();
    }

    @Override
    public void invalidateFavourites() {
        cachedFavourites.clear();
    }

    @Override
    public void invalidate() {
        cachedRoutes.clear();
        cachedFavourites.clear();
        cachedRouteServices.clear();
        cachedBusStopServices.clear();
        cachedDetailedBusStops.clear();
    }

}
