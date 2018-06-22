package ie.dublinbuspal.android;

import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;
import ie.dublinbuspal.android.data.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class MockRemoteDataSource implements RemoteDataSource {

    private List<BusStop> busStops;
    private List<Route> routes;
    private Map<String, BusStopService> busStopServices;
    private Map<String, RouteService> routeServices;
    private Map<String, List<RealTimeData>> realtimeData;

    public MockRemoteDataSource() {
        super();
    }

    @Override
    public List<BusStop> getBusStops() throws Exception {
        if (busStops == null) {
            busStops = new ArrayList<>();
            busStops.add(new BusStop("1", "Test Bus Stop 1",
                    53.348176, -6.295140));
            busStops.add(new BusStop("2", "Test Bus Stop 2",
                    53.348096, -6.295356));
            busStops.add(new BusStop("3", "Test Bus Stop 3",
                    53.311451, -6.220443));
            busStops.add(new BusStop("4", "Test Bus Stop 4",
                    53.311372, -6.220865));
            busStops.add(new BusStop("5", "Test Bus Stop 5",
                    53.294664, -6.134293));
            busStops.add(new BusStop("6", "Test Bus Stop 6",
                    53.294523, -6.134266));
        }
        return busStops;
    }

    @Override
    public List<Route> getRoutes() throws Exception {
        if (routes == null) {
            routes = new ArrayList<>();
            routes.add(new Route("46A", "Phoenix Park", "Dun Laoghaire"));
        }
        return routes;
    }

    @Override
    public BusStopService getBusStopService(String stopId) throws Exception {
        if (busStopServices == null) {
            busStopServices = new HashMap<>();
            busStopServices.put("1",
                    new BusStopService("1", asList("46A", "145")));
            busStopServices.put("2",
                    new BusStopService("2", asList("46A", "145")));
            busStopServices.put("3",
                    new BusStopService("3", asList("46A", "145")));
            busStopServices.put("4",
                    new BusStopService("4", asList("46A", "145")));
            busStopServices.put("5",
                    new BusStopService("5", asList("46A", "145")));
            busStopServices.put("6",
                    new BusStopService("6", asList("46A", "145")));
        }
        return busStopServices.get(stopId);
    }

    @Override
    public RouteService getRouteService(String routeId) throws Exception {
        if (routeServices == null) {
            routeServices = new HashMap<>();
            routeServices.put("46A", new RouteService("46A",
                    "Phoenix Park To Dun Laoghaire", "Phoenix Park",
                    "Dun Laoghaire", asList("1", "3", "5"),
                    asList("6", "4", "2")));
            routeServices.put("145", new RouteService("145",
                    "Phoenix Park To Dun Laoghaire", "Phoenix Park",
                    "Dun Laoghaire", asList("1", "3", "5"),
                    asList("6", "4", "2")));
        }
        return routeServices.get(routeId);
    }

    @Override
    public List<RealTimeData> getRealTimeData(String stopId) throws Exception {
        if (realtimeData == null) {
            realtimeData = new HashMap<>();
            List<RealTimeData> realtimeData1 = new ArrayList<>();
            realtimeData1.add(new RealTimeData("41",
                    "Swords Manor via Dublin Airport",
                    "2017-10-06T21:59:38+01:00",
                    "2017-10-06T21:56:00.983+01:00"));
            realtimeData1.add(new RealTimeData("1",
                    "Shanard Road via O'Connell Street",
                    "2017-10-06T22:02:37+01:00",
                    "2017-10-06T21:56:00.997+01:00"));
            realtimeData1.add(new RealTimeData("16",
                    "Dublin Airport via O'Connell Street",
                    "2017-10-06T22:04:27+01:00",
                    "2017-10-06T21:56:00.997+01:00"));
            realtimeData1.add(new RealTimeData("41C",
                    "Swords Manor via Drumcondra",
                    "2017-10-06T22:09:15+01:00",
                    "2017-10-06T21:56:00.997+01:00"));
            realtimeData1.add(new RealTimeData("33",
                    "Balbriggan via Skerries",
                    "2017-10-06T22:09:26+01:00",
                    "2017-10-06T21:56:01.013+01:00"));
            realtimeData1.add(new RealTimeData("16",
                    "Dublin Airport via O'Connell Street",
                    "2017-10-06T22:21:17+01:00",
                    "2017-10-06T21:56:01.013+01:00"));
            realtimeData1.add(new RealTimeData("11",
                    "St Pappin's Road via Drumcondra",
                    "2017-10-06T22:23:34+01:00",
                    "2017-10-06T21:56:01.03+01:00"));
            realtimeData1.add(new RealTimeData("41",
                    "Swords Manor via Dublin Airport",
                    "2017-10-06T22:25:05+01:00",
                    "2017-10-06T21:56:01.03+01:00"));
            realtimeData1.add(new RealTimeData("1",
                    "Shanard Road via O'Connell Street",
                    "2017-10-06T22:32:32+01:00",
                    "2017-10-06T21:56:01.03+01:00"));
            realtimeData1.add(new RealTimeData("16",
                    "Dublin Airport via O'Connell Street",
                    "2017-10-06T22:36:53+01:00",
                    "2017-10-06T21:56:01.03+01:00"));
            realtimeData1.add(new RealTimeData("41C",
                    "Swords Manor via Drumcondra",
                    "2017-10-06T22:39:15+01:00",
                    "2017-10-06T21:56:01.03+01:00"));
            realtimeData1.add(new RealTimeData("33",
                    "Balbriggan via Skerries",
                    "2017-10-06T22:39:26+01:00",
                    "2017-10-06T21:56:01.043+01:00"));
            realtimeData1.add(new RealTimeData("11",
                    "St Pappin's Road via Drumcondra",
                    "2017-10-06T22:43:15+01:00",
                    "2017-10-06T21:56:01.043+01:00"));
            realtimeData1.add(new RealTimeData("44",
                    "DCU via O'Connell Street",
                    "2017-10-06T22:43:45.747+01:00",
                    "2017-10-06T21:56:01.043+01:00"));
            realtimeData1.add(new RealTimeData("41",
                    "Swords Manor via Dublin Airport",
                    "2017-10-06T22:55:05+01:00",
                    "2017-10-06T21:56:01.06+01:00"));
            realtimeData.put("1", realtimeData1);
            List<RealTimeData> realtimeData2 = new ArrayList<>();
            realtimeData.put("2", realtimeData2);
            List<RealTimeData> realtimeData3 = new ArrayList<>();
            realtimeData3.add(new RealTimeData("46A",
                    "Dun Laoghaire via City Centre",
                    "2017-10-06T21:59:38+01:00",
                    "2017-10-06T21:56:00.983+01:00"));
            realtimeData3.add(new RealTimeData("145",
                    "Dun Laoghaire via City Centre",
                    "2017-10-06T22:11:38+01:00",
                    "2017-10-06T21:56:00.983+01:00"));
            realtimeData.put("3", realtimeData3);
            List<RealTimeData> realtimeData4 = new ArrayList<>();
            realtimeData.put("4", realtimeData4);
            List<RealTimeData> realtimeData5 = new ArrayList<>();
            realtimeData.put("5", realtimeData5);
            List<RealTimeData> realtimeData6 = new ArrayList<>();
            realtimeData.put("6", realtimeData6);
        }
        return realtimeData.get(stopId);
    }

}
