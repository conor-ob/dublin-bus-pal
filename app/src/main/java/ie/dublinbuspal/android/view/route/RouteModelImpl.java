package ie.dublinbuspal.android.view.route;

import java.util.List;

import ie.dublinbuspal.model.routeservice.RouteService;
import ie.dublinbuspal.model.stop.Stop;

public class RouteModelImpl implements RouteModel {

    private RouteService routeService;
    private String routeId;
    private String stopId;

    public RouteModelImpl() {
        super();
    }

    @Override
    public String getRouteId() {
        return routeId;
    }

    @Override
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public RouteService getRouteService() {
        return routeService;
    }

    @Override
    public List<Stop> getDetailedBusStops() {
        return routeService.getVariants().get(0).getStops();
    }

    @Override
    public void changeDirection() {
        if (isBiDirectional()) {
//            isDisplayOutboundStops = !isDisplayOutboundStops;
        }
    }

    @Override
    public boolean isBiDirectional() {
//        return !CollectionUtils.isNullOrEmpty(routeService
//                .getInboundStopIds()) && !CollectionUtils
//                .isNullOrEmpty(routeService.getOutboundStopIds());
        return false;
    }

    @Override
    public String getTowards() {
//        return isDisplayOutboundStops ? routeService.getDestination() : routeService.getOrigin();
        return routeService.getVariants().get(0).getDestination();
    }

    @Override
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @Override
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

//    private boolean isDisplayOutboundStops() {
//        if (stopId == null) {
//          return !routeService.getOutboundStopIds().isEmpty();
//        }
//        return routeService.getOutboundStopIds().contains(stopId)
//                && !routeService.getOutboundStopIds().isEmpty();
//    }

    @Override
    public String getOrigin() {
//        return isDisplayOutboundStops ? routeService.getOrigin() : routeService.getDestination();
        return routeService.getVariants().get(0).getOrigin();
    }

    @Override
    public String getDestination() {
//        return isDisplayOutboundStops ? routeService.getDestination() : routeService.getOrigin();
        return routeService.getVariants().get(0).getDestination();
    }

}
