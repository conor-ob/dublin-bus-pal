package ie.dublinbuspal.android.data;

import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.DetailedRouteService;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;
import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;
import ie.dublinbuspal.android.data.remote.rss.xml.Rss;

import java.util.List;
import java.util.Map;

public interface DublinBusRepository {

    List<BusStop> getBusStops() throws Exception;

    List<BusStop> getBusStopsRemote() throws Exception;

    List<Route> getRoutes() throws Exception;

    List<Route> getRoutesRemote() throws Exception;

    List<UncheckedBusStopService> getBusStopServices() throws Exception;

    List<UncheckedBusStopService> getBusStopServicesRemote() throws Exception;

    DetailedBusStop getDetailedBusStop(String stopId) throws Exception;

    List<DetailedBusStop> getDetailedBusStops() throws Exception;

    List<DetailedBusStop> getDetailedBusStops(List<String> stopIds) throws Exception;

    BusStopService getBusStopService(String stopId) throws Exception;

    RouteService getRouteService(String routeId) throws Exception;

    DetailedRouteService getDetailedRouteService(String routeId) throws Exception;

    List<RealTimeData> getRealTimeData(String stopId) throws Exception;

    Map<String, List<RealTimeData>> getCondensedRealTimeData(String stopId) throws Exception;

    List<DetailedBusStop> getFavourites();

    Rss getRss() throws Exception;

    boolean saveBusStopAsFavourite(String stopID, String customName, List<String> customRoutes);

    boolean removeFavourite(String stopId);

    void invalidateCache();

}
