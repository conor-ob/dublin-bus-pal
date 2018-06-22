package ie.dublinbuspal.android.view.realtime;

import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;

import java.util.List;
import java.util.Set;

public interface RealTimeModel {

    void setStopId(String stopId);

    void setBusStop(DetailedBusStop busStop);

    void setBusStopService(BusStopService busStopService);

    void setAdjustedBusStopService(BusStopService service);

    void setRealTimeData(List<RealTimeData> realTimeData);

    void addOrRemoveRouteFilter(String route);

    String getStopId();

    DetailedBusStop getBusStop();

    BusStopService getBusStopService();

    BusStopService getAdjustedBusStopService();

    List<RealTimeData> getRealTimeData();

    Set<String> getRouteFilters();

}
