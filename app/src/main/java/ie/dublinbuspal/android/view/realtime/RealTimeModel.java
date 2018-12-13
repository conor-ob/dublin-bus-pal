package ie.dublinbuspal.android.view.realtime;

import java.util.List;
import java.util.Set;

import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.model.livedata.LiveData;
import ie.dublinbuspal.model.stop.Stop;

public interface RealTimeModel {

    void setStopId(String stopId);

    void setBusStop(Stop busStop);

    void setBusStopService(BusStopService busStopService);

    void setAdjustedBusStopService(BusStopService service);

    void setRealTimeData(List<LiveData> realTimeData);

    void addOrRemoveRouteFilter(String route);

    String getStopId();

    Stop getBusStop();

    BusStopService getBusStopService();

    BusStopService getAdjustedBusStopService();

    List<LiveData> getRealTimeData();

    Set<String> getRouteFilters();

}
