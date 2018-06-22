package ie.dublinbuspal.android.view.route;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.DetailedRouteService;
import ie.dublinbuspal.android.util.CollectionUtilities;

import java.util.List;

import static ie.dublinbuspal.android.util.ObjectUtilities.safeEquals;

public class RouteModelImpl implements RouteModel {

    private DetailedRouteService routeService;
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
    public void setRouteService(DetailedRouteService routeService) {
        if (safeEquals(this.routeService, routeService)) {
            return;
        }
        this.routeService = routeService;
        this.isDisplayOutboundStops = isDisplayOutboundStops();
    }

    @Override
    public DetailedRouteService getRouteService() {
        return routeService;
    }

    @Override
    public List<DetailedBusStop> getDetailedBusStops() {
        return isDisplayOutboundStops ? routeService.getOutboundBusStops()
                : routeService.getInboundBusStops();
    }

    @Override
    public void changeDirection() {
        if (isBiDirectional()) {
            isDisplayOutboundStops = !isDisplayOutboundStops;
        }
    }

    @Override
    public boolean isBiDirectional() {
        return !CollectionUtilities.isNullOrEmpty(routeService.getRouteService()
                .getInboundStopIds()) && !CollectionUtilities
                .isNullOrEmpty(routeService.getRouteService().getOutboundStopIds());
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
          return !routeService.getRouteService().getOutboundStopIds().isEmpty();
        }
        return routeService.getRouteService().getOutboundStopIds().contains(stopId)
                && !routeService.getRouteService().getOutboundStopIds().isEmpty();
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
