package ie.dublinbuspal.android.view.route;

import java.util.List;

import ie.dublinbuspal.util.CollectionUtilities;
import ie.dublinbuspal.model.routeservice.RouteService;
import ie.dublinbuspal.model.stop.Stop;

import static ie.dublinbuspal.util.ObjectUtilities.safeEquals;

public class RouteModelImpl implements RouteModel {

    private RouteService routeService;
    private String routeId;
    private String stopId;
    private boolean isDisplayOutboundStops;

    public RouteModelImpl() {
        super();
    }

    @Override
    public String getRouteId() {
        return routeId;
    }

    @Override
    public void setRouteService(RouteService routeService) {
        if (safeEquals(this.routeService, routeService)) {
            return;
        }
        this.routeService = routeService;
        this.isDisplayOutboundStops = isDisplayOutboundStops();
    }

    @Override
    public RouteService getRouteService() {
        return routeService;
    }

    @Override
    public List<Stop> getDetailedBusStops() {
        return isDisplayOutboundStops ? routeService.getOutboundStops()
                : routeService.getInboundStops();
    }

    @Override
    public void changeDirection() {
        if (isBiDirectional()) {
            isDisplayOutboundStops = !isDisplayOutboundStops;
        }
    }

    @Override
    public boolean isBiDirectional() {
        return !CollectionUtilities.isNullOrEmpty(routeService
                .getInboundStopIds()) && !CollectionUtilities
                .isNullOrEmpty(routeService.getOutboundStopIds());
    }

    @Override
    public String getTowards() {
        return isDisplayOutboundStops ? routeService.getDestination() : routeService.getOrigin();
    }

    @Override
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @Override
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    private boolean isDisplayOutboundStops() {
        if (stopId == null) {
          return !routeService.getOutboundStopIds().isEmpty();
        }
        return routeService.getOutboundStopIds().contains(stopId)
                && !routeService.getOutboundStopIds().isEmpty();
    }

    @Override
    public String getOrigin() {
        return isDisplayOutboundStops ? routeService.getOrigin() : routeService.getDestination();
    }

    @Override
    public String getDestination() {
        return isDisplayOutboundStops ? routeService.getDestination() : routeService.getOrigin();
    }

}
