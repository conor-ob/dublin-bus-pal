package ie.dublinbuspal.android.data;

import ie.dublinbuspal.android.data.local.LocalDataSource;
import ie.dublinbuspal.android.data.local.PreferencesDataSource;
import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.DetailedRouteService;
import ie.dublinbuspal.android.data.local.entity.FavouriteBusStop;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;
import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;
import ie.dublinbuspal.android.data.memory.CacheDataSource;
import ie.dublinbuspal.android.data.remote.RemoteDataSource;
import ie.dublinbuspal.android.data.remote.rest.RestDataSource;
import ie.dublinbuspal.android.data.remote.rss.RssDataSource;
import ie.dublinbuspal.android.data.remote.rss.xml.Rss;
import ie.dublinbuspal.android.util.InternetManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ie.dublinbuspal.android.util.CollectionUtilities.isNullOrEmpty;

public class DublinBusRepositoryImpl extends DublinBusRepositoryLocks
        implements DublinBusRepository {

    private final CacheDataSource cache;
    private final LocalDataSource database;
    private final RemoteDataSource soapService;
    private final RestDataSource restApi;
    private final RssDataSource rssFeed;
    private final PreferencesDataSource preferences;
    private final InternetManager internetManager;

    public DublinBusRepositoryImpl(CacheDataSource cache, LocalDataSource database,
                                   RemoteDataSource soapService, RestDataSource restApi,
                                   RssDataSource rssFeed, PreferencesDataSource preferences,
                                   InternetManager internetManager) {
        super();
        this.cache = cache;
        this.database = database;
        this.soapService = soapService;
        this.restApi = restApi;
        this.rssFeed = rssFeed;
        this.preferences = preferences;
        this.internetManager = internetManager;
    }

    @Override
    public List<BusStop> getBusStops() throws Exception {
        synchronized (getBusStopsLock()) {
            List<BusStop> busStops = database.getBusStops();
            if (isNullOrEmpty(busStops) || needsUpdate()) {
                busStops = getBusStopsRemote();
            }
            return busStops;
        }
    }

    @Override
    public List<BusStop> getBusStopsRemote() throws Exception {
        List<BusStop> busStops = soapService.getBusStops();
        database.replaceBusStops(busStops);
        preferences.setDatabaseLastUpdatedTime(new Date().getTime());
        return busStops;
    }

    @Override
    public List<Route> getRoutes() throws Exception {
        synchronized (getRoutesLock()) {
            List<Route> routes = cache.getRoutes();
            if (isNullOrEmpty(routes)) {
                routes = database.getRoutes();
                if (isNullOrEmpty(routes) || needsUpdate()) {
                    routes = getRoutesRemote();
                }
                cache.cacheRoutes(routes);
            }
            return routes;
        }
    }

    @Override
    public List<Route> getRoutesRemote() throws Exception {
        List<Route> routes = soapService.getRoutes();
        database.replaceRoutes(routes);
        return routes;
    }

    @Override
    public List<UncheckedBusStopService> getBusStopServices() throws Exception {
        List<UncheckedBusStopService> uncheckedBusStopServices =
                database.getUncheckedBusStopServices();
        if (isNullOrEmpty(uncheckedBusStopServices) || needsUpdate()) {
            uncheckedBusStopServices = getBusStopServicesRemote();
            cache.invalidateDetailedBusStops();
        }
        return uncheckedBusStopServices;
    }

    @Override
    public List<UncheckedBusStopService> getBusStopServicesRemote() throws Exception {
        List<UncheckedBusStopService> uncheckedBusStopServices =
                restApi.getUncheckedBusStopServices();
        database.replaceBusStopServices(uncheckedBusStopServices);
        return uncheckedBusStopServices;
    }

    @Override
    public List<DetailedBusStop> getDetailedBusStops() throws Exception {
        synchronized (getBusStopsLock()) {
            List<DetailedBusStop> detailedBusStops = cache.getDetailedBusStops();
            if (isNullOrEmpty(detailedBusStops)) {
                detailedBusStops = database.getDetailedBusStops();
                if (isNullOrEmpty(detailedBusStops) || needsUpdate()) {
                    getBusStops();
                    detailedBusStops = database.getDetailedBusStops();
                }
                cache.cacheDetailedBusStops(detailedBusStops);
            }
            return detailedBusStops;
        }
    }

    @Override
    public DetailedBusStop getDetailedBusStop(String stopId) throws Exception {
        DetailedBusStop busStop = cache.getDetailedBusStop(stopId);
        if (busStop == null) {
            getDetailedBusStops();
        }
        return cache.getDetailedBusStop(stopId);
    }

    @Override
    public List<DetailedBusStop> getDetailedBusStops(List<String> stopIds) throws Exception {
        List<DetailedBusStop> busStops = new ArrayList<>();
        for (String stopId : stopIds) {
            busStops.add(getDetailedBusStop(stopId));
        }
        return busStops;
    }

    @Override
    public BusStopService getBusStopService(String stopId) throws Exception {
        BusStopService busStopService = cache.getBusStopService(stopId);
        if (busStopService == null) {
            busStopService = database.getBusStopService(stopId);
            if (busStopService == null || needsUpdate()) {
                busStopService = soapService.getBusStopService(stopId);
                if (busStopService != null) {
                    database.insertBusStopService(busStopService);
                }
            }
            cache.cacheBusStopService(busStopService);
        }
        return busStopService;
    }

    @Override
    public RouteService getRouteService(String routeId) throws Exception {
        RouteService routeService = cache.getRouteService(routeId);
        if (routeService == null) {
            routeService = database.getRouteService(routeId);
            if (routeService == null || needsUpdate()) {
                routeService = soapService.getRouteService(routeId);
                if (routeService != null) {
                    database.insertRouteService(routeService);
                }
            }
            cache.cacheRouteService(routeService);
        }
        return routeService;
    }

    @Override
    public DetailedRouteService getDetailedRouteService(String routeId)
            throws Exception {
        RouteService routeService = getRouteService(routeId);
        List<DetailedBusStop> inboundStops = getDetailedBusStops(routeService.getInboundStopIds());
        List<DetailedBusStop> outboundStops = getDetailedBusStops(routeService.getOutboundStopIds());
        return new DetailedRouteService(routeService, inboundStops, outboundStops);
    }

    @Override
    public List<RealTimeData> getRealTimeData(String stopId) throws Exception {
        return soapService.getRealTimeData(stopId);
    }

    @Override
    public Map<String, List<RealTimeData>> getCondensedRealTimeData(String stopId)
            throws Exception {
        Map<String, List<RealTimeData>> condensedRealTimeData = new LinkedHashMap<>();
        List<RealTimeData> realTimeData = getRealTimeData(stopId);
        for (RealTimeData data : realTimeData) {
            List<RealTimeData> routeRealTimeData = condensedRealTimeData.get(data.getRoute());
            if (routeRealTimeData == null) {
                routeRealTimeData = new ArrayList<>();
                condensedRealTimeData.put(data.getRoute(), routeRealTimeData);
            }
            routeRealTimeData.add(data);
        }
        return condensedRealTimeData;
    }

    @Override
    public List<DetailedBusStop> getFavourites() {
        List<DetailedBusStop> favourites = cache.getFavourites();
        if (isNullOrEmpty(favourites)) {
            favourites = database.getFavourites();
            if (!isNullOrEmpty(favourites)) {
                cache.cacheFavourites(favourites);
            }
        }
        return favourites;
    }

    @Override
    public Rss getRss() throws Exception {
        return rssFeed.getRss();
    }

    @Override
    public boolean saveBusStopAsFavourite(String id, String customName, List<String> customRoutes) {
        FavouriteBusStop favouriteBusStop = new FavouriteBusStop(id, customName, customRoutes, 0);
        database.insertFavourite(favouriteBusStop);
        cache.invalidateFavourites();
        cache.invalidateDetailedBusStops();
        return true;
    }

    @Override
    public boolean removeFavourite(String stopId) {
        cache.invalidateFavourites();
        cache.invalidateDetailedBusStops();
        FavouriteBusStop favourite = database.selectFavourite(stopId);
        return database.deleteFavourite(favourite);
    }

    @Override
    public void invalidateCache() {
        cache.invalidate();
    }

    private boolean needsUpdate() {
        return preferences.isDatabaseAutoUpdate() && databaseNeedsUpdate() && canUpdate();
    }

    private boolean databaseNeedsUpdate() {
        long databaseLastUpdatedTime = preferences.getDatabaseLastUpdatedTime();
        long databaseUpdateFrequency = preferences.getDatabaseUpdateFrequency();
        long now = System.currentTimeMillis();
        long timeSinceLastUpdate = now - databaseLastUpdatedTime;
        return timeSinceLastUpdate > databaseUpdateFrequency;
    }

    private boolean canUpdate() {
        boolean updateOnlyOnGoodConnection = preferences.isUpdateDatabaseOnlyOnGoodConnection();
        if (updateOnlyOnGoodConnection) {
            return internetManager.hasGoodConnection();
        }
        return internetManager.hasOkConnection();
    }

}
