package ie.dublinbuspal.android.view.route;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.DetailedRouteService;

import java.util.List;

public interface RouteModel {

    String getRouteId();

    void setRouteService(DetailedRouteService routeService);

    DetailedRouteService getRouteService();

    List<DetailedBusStop> getDetailedBusStops();

    void changeDirection();

    String getTowards();

    void setRouteId(String routeId);

    void setStopId(String stopId);

    boolean isBiDirectional();

    String getOrigin();

    String getDestination();

}
