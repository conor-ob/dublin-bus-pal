package ie.dublinbuspal.android.view.realtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.model.livedata.LiveData;
import ie.dublinbuspal.model.stop.ResolvedStop;

public class RealTimeModelImpl implements RealTimeModel {

    private String stopId;
    private ResolvedStop busStop;
    private BusStopService busStopService;
    private BusStopService adjustedBusStopService;
    private List<LiveData> realTimeData;
    private Set<String> routeFilters;

    public RealTimeModelImpl() {
        super();
    }

    @Override
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    @Override
    public void setBusStop(ResolvedStop busStop) {
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
    public void setRealTimeData(List<LiveData> realTimeData) {
        this.realTimeData = realTimeData;
    }

    @Override
    public String getStopId() {
        return stopId;
    }

    @Override
    public ResolvedStop getBusStop() {
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
    public List<LiveData> getRealTimeData() {
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

    private List<LiveData> filterOn(Set<String> routeFilters) {
        List<LiveData> filtered = new ArrayList<>();
        for (LiveData realTimeData : this.realTimeData) {
            if (routeFilters.contains(realTimeData.getRouteId())) {
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
