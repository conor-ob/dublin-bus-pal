package ie.dublinbuspal.android.data.remote;

import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;

import java.util.List;

public interface RemoteDataSource {

    List<RealTimeData> getRealTimeData(String stopId) throws Exception;

    List<BusStop> getBusStops() throws Exception;

    List<Route> getRoutes() throws Exception;

    RouteService getRouteService(String routeId) throws Exception;

    BusStopService getBusStopService(String stopId) throws Exception;

}
