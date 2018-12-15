package ie.dublinbuspal.android.view.route;

import java.util.List;

import ie.dublinbuspal.model.routeservice.RouteService;
import ie.dublinbuspal.model.stop.Stop;

public interface RouteModel {

    String getRouteId();

    void setRouteService(RouteService routeService);

    RouteService getRouteService();

    List<Stop> getDetailedBusStops();

    void changeDirection();

    String getTowards();

    void setRouteId(String routeId);

    void setStopId(String stopId);

    boolean isBiDirectional();

    String getOrigin();

    String getDestination();

}
