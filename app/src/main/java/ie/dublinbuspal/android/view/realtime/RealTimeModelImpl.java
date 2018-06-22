package ie.dublinbuspal.android.view.realtime;

import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import ie.dublinbuspal.android.util.CollectionUtilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RealTimeModelImpl implements RealTimeModel {

    private String stopId;
    private DetailedBusStop busStop;
    private BusStopService busStopService;
    private BusStopService adjustedBusStopService;
    private List<RealTimeData> realTimeData;
    private Set<String> routeFilters;

    public RealTimeModelImpl() {
        super();
    }

    @Override
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    @Override
    public void setBusStop(DetailedBusStop busStop) {
        this.busStop = busStop;
    }

    @Override
    public void setBusStopService(BusStopService busStopService) {
        this.busStopService = busStopService;
    }

    @Override
    public void setAdjustedBusStopService(BusStopService adjustedBusStopService) {
        this.adjustedBusStopService = adjustedBusStopService;
    }

    @Override
    public void setRealTimeData(List<RealTimeData> realTimeData) {
        this.realTimeData = realTimeData;
    }

    @Override
    public String getStopId() {
        return stopId;
    }

    @Override
    public DetailedBusStop getBusStop() {
        return busStop;
    }

    @Override
    public BusStopService getBusStopService() {
        return busStopService;
    }

    @Override
    public BusStopService getAdjustedBusStopService() {
        if (adjustedBusStopService == null) {
            return busStopService;
        }
        return adjustedBusStopService;
    }

    @Override
    public List<RealTimeData> getRealTimeData() {
        if (CollectionUtilities.isNullOrEmpty(getRouteFilters())) {
            return filterOn(CollectionUtilities.toSet(getAdjustedBusStopService().getRoutes()));
        }
        return filterOn(getRouteFilters());
    }

    @Override
    public Set<String> getRouteFilters() {
        if (routeFilters == null) {
            routeFilters = new HashSet<>();
        }
        return routeFilters;
    }

    private List<RealTimeData> filterOn(Set<String> routeFilters) {
        List<RealTimeData> filtered = new ArrayList<>();
        for (RealTimeData realTimeData : this.realTimeData) {
            if (routeFilters.contains(realTimeData.getRoute())) {
                filtered.add(realTimeData);
            }
        }
        return filtered;
    }

    @Override
    public void addOrRemoveRouteFilter(String route) {
        if (getRouteFilters().contains(route)) {
            getRouteFilters().remove(route);
        } else {
            getRouteFilters().add(route);
        }
    }

}
