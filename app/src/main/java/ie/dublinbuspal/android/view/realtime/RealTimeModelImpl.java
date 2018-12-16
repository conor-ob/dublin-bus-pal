package ie.dublinbuspal.android.view.realtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ie.dublinbuspal.util.CollectionUtilities;
import ie.dublinbuspal.model.livedata.LiveData;
import ie.dublinbuspal.model.stop.Stop;

public class RealTimeModelImpl implements RealTimeModel {

    private String stopId;
    private Stop busStop;
    private List<String> busStopService;
    private List<String> adjustedBusStopService;
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
    public void setBusStop(Stop busStop) {
        this.busStop = busStop;
    }

    @Override
    public void setBusStopService(List<String> busStopService) {
        this.busStopService = busStopService;
    }

    @Override
    public void setAdjustedBusStopService(List<String> adjustedBusStopService) {
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
    public Stop getBusStop() {
        return busStop;
    }

    @Override
    public List<String> getBusStopService() {
        return busStopService;
    }

    @Override
    public List<String> getAdjustedBusStopService() {
        if (adjustedBusStopService == null) {
            return busStopService;
        }
        return adjustedBusStopService;
    }

    @Override
    public List<LiveData> getRealTimeData() {
        if (CollectionUtilities.isNullOrEmpty(getRouteFilters())) {
            return filterOn(CollectionUtilities.toSet(getAdjustedBusStopService()));
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
