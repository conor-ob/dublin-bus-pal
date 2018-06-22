package ie.dublinbuspal.android.view.search;

import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.data.local.entity.Route;

import java.util.Collections;
import java.util.List;

import static ie.dublinbuspal.android.util.ObjectUtilities.safeEquals;

public class SearchModelImpl implements SearchModel {

    private List<Route> routes;
    private List<DetailedBusStop> busStops;

    public SearchModelImpl() {
        super();
    }

    @Override
    public void setSearchableRoutes(List<Route> routes) {
        if (safeEquals(this.routes, routes)) {
            return;
        }
        this.routes = routes;
    }

    @Override
    public void setSearchableBusStops(List<DetailedBusStop> busStops) {
        if (safeEquals(this.busStops, busStops)) {
            return;
        }
        this.busStops = busStops;
    }

    @Override
    public List<Route> getSearchableRoutes() {
        if (routes == null) {
            return Collections.emptyList();
        }
        return routes;
    }

    @Override
    public List<DetailedBusStop> getSearchableBusStops() {
        if (busStops == null) {
            return Collections.emptyList();
        }
        return busStops;
    }

}
