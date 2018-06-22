package ie.dublinbuspal.android.view.search;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.Route;

import java.util.List;

public interface SearchModel {

    void setSearchableRoutes(List<Route> routes);

    void setSearchableBusStops(List<DetailedBusStop> busStops);

    List<Route> getSearchableRoutes();

    List<DetailedBusStop> getSearchableBusStops();

}
