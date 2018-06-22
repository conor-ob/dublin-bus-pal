package ie.dublinbuspal.android.data.remote.soap;

import ie.dublinbuspal.android.data.local.entity.BusStop;
import ie.dublinbuspal.android.data.local.entity.BusStopService;
import ie.dublinbuspal.android.data.local.entity.RealTimeData;
import ie.dublinbuspal.android.data.local.entity.Route;
import ie.dublinbuspal.android.data.local.entity.RouteService;
import ie.dublinbuspal.android.data.remote.RemoteDataSource;
import ie.dublinbuspal.android.data.remote.soap.xml.realtime.RealTimeDataRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.realtime.RealTimeDataRequestBody;
import ie.dublinbuspal.android.data.remote.soap.xml.realtime.RealTimeDataRequestRoot;
import ie.dublinbuspal.android.data.remote.soap.xml.realtime.RealTimeDataResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.realtime.RealTimeDataXml;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceInboundStopXml;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceOutboundStopXml;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceRequestBody;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceRequestRoot;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceXml;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteXml;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RoutesRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RoutesRequestBody;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RoutesRequestRoot;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RoutesResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopServiceRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopServiceRequestBody;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopServiceRequestRoot;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopServiceResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopXml;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopsRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopsRequestBody;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopsRequestRoot;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopsResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.test.TestServiceRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.test.TestServiceRequestBody;
import ie.dublinbuspal.android.data.remote.soap.xml.test.TestServiceRequestRoot;
import ie.dublinbuspal.android.data.remote.soap.xml.test.TestServiceResponse;
import ie.dublinbuspal.android.util.AlphanumComparator;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.android.util.StringUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Response;

public class DublinBusSoapServiceAdapter implements RemoteDataSource {

    private final DublinBusSoapServiceApi api;

    public DublinBusSoapServiceAdapter(DublinBusSoapServiceApi api) {
        this.api = api;
    }

    @Override
    public List<BusStop> getBusStops() throws Exception {
        List<BusStop> busStops = new ArrayList<>();
        BusStopsRequest request = new BusStopsRequest();
        BusStopsRequestBody requestBody = new BusStopsRequestBody();
        BusStopsRequestRoot requestRoot = new BusStopsRequestRoot();
        requestBody.setBusStopsRequestRoot(requestRoot);
        request.setBusStopsRequestBody(requestBody);
        Response<BusStopsResponse> response = api.getBusStops(request).execute();
        if (response.isSuccessful()) {
            BusStopsResponse busStopsResponse = response.body();
            if (busStopsResponse != null) {
                List<BusStopXml> busStopXmls = busStopsResponse.getBusStops();
                for (BusStopXml busStopXml : busStopXmls) {
                    busStops.add(Mapper.toBusStop(busStopXml));
                }
            }
        }
        return busStops;
    }

    @Override
    public List<Route> getRoutes() throws Exception {
        List<Route> routes = new ArrayList<>();
        RoutesRequest requestEnvelope = new RoutesRequest();
        RoutesRequestBody requestBody = new RoutesRequestBody();
        RoutesRequestRoot requestData = new RoutesRequestRoot();
        requestData.setFilter(StringUtilities.EMPTY_STRING);
        requestBody.setRoutesRequestRoot(requestData);
        requestEnvelope.setRoutesRequestBody(requestBody);
        Response<RoutesResponse> response = api.getRoutes(requestEnvelope).execute();
        if (response.isSuccessful()) {
            RoutesResponse responseEnvelope = response.body();
            if (responseEnvelope != null) {
                List<RouteXml> routeXmls = responseEnvelope.getRoutes();
                for (RouteXml routeXml : routeXmls) {
                    if (sanityCheck(routeXml)) {
                        routes.add(Mapper.toRoute(routeXml));
                    }
                }
            }
        }
        return routes;
    }

    @Override
    public List<RealTimeData> getRealTimeData(String stopId) throws Exception {
        assertWebServiceAvailable();
        List<RealTimeData> realTimeData = new ArrayList<>();
        RealTimeDataRequest request = new RealTimeDataRequest();
        RealTimeDataRequestBody requestBody = new RealTimeDataRequestBody();
        RealTimeDataRequestRoot requestRoot = new RealTimeDataRequestRoot();
        requestRoot.setStopId(stopId);
        requestRoot.setForceRefresh(Boolean.toString(true).toLowerCase());
        requestBody.setRealTimeDataRequestRoot(requestRoot);
        request.setGetRealTimeDataRequestBody(requestBody);
        Response<RealTimeDataResponse> response = api.getRealTimeData(request).execute();
        if (response.isSuccessful()) {
            RealTimeDataResponse realTimeDataResponse = response.body();
            if (realTimeDataResponse != null) {
                List<RealTimeDataXml> realTimeDataXmls = realTimeDataResponse.getRealTimeData();
                if (realTimeDataXmls != null) {
                    for (RealTimeDataXml realTimeDataXml : realTimeDataXmls) {
                        realTimeData.add(Mapper.toRealTimeData(realTimeDataXml));
                    }
                }
            }
        }
        return realTimeData;
    }

    @Override
    public BusStopService getBusStopService(String stopId) throws Exception {
        List<String> routes = new ArrayList<>();
        Set<String> set = new HashSet<>();
        BusStopServiceRequest request = new BusStopServiceRequest();
        BusStopServiceRequestBody requestBody = new BusStopServiceRequestBody();
        BusStopServiceRequestRoot requestRoot = new BusStopServiceRequestRoot();
        requestRoot.setStopId(stopId);
        requestBody.setBusStopServiceRequestRoot(requestRoot);
        request.setBusStopServiceRequestBody(requestBody);
        Response<BusStopServiceResponse> response = api.getBusStopService(request).execute();
        if (response.isSuccessful()) {
            BusStopServiceResponse busStopServiceResponse = response.body();
            if (busStopServiceResponse != null) {
                List<RouteXml> routeXmls = busStopServiceResponse.getRouteXmls();
                for (RouteXml routeXml : routeXmls) {
                    set.add(routeXml.getRouteID().trim().toUpperCase());
                }
                routes = new ArrayList<>(set);
            }
        }
        Collections.sort(routes, AlphanumComparator.getInstance());
        return new BusStopService(stopId, routes);
    }

    @Override
    public RouteService getRouteService(String routeId) throws Exception {
        RouteService routeService = null;
        RouteServiceRequest request = new RouteServiceRequest();
        RouteServiceRequestBody requestBody = new RouteServiceRequestBody();
        RouteServiceRequestRoot requestRoot = new RouteServiceRequestRoot();
        requestRoot.setRoute(routeId);
        requestBody.setRouteServiceRequestRoot(requestRoot);
        request.setRouteServiceRequestBody(requestBody);
        Response<RouteServiceResponse> response = api.getRouteService(request).execute();
        if (response.isSuccessful()) {
            RouteServiceResponse routeServiceResponse = response.body();
            if (routeServiceResponse != null) {
                RouteServiceXml routeServiceXml = routeServiceResponse.getRouteServiceXml();
                List<RouteServiceInboundStopXml> inboundStopXmls =
                        routeServiceResponse.getInboundStopXmls();
                List<RouteServiceOutboundStopXml> outboundStopXmls =
                        routeServiceResponse.getOutboundStopXmls();
                routeService = new RouteService(routeId,
                        routeServiceXml.getRouteDescription().trim(),
                        routeServiceXml.getRouteOrigin().trim(),
                        routeServiceXml.getRouteDestination().trim(),
                        Mapper.toInboundStopIds(inboundStopXmls),
                        Mapper.toOutboundStopIds(outboundStopXmls));
            }
        }
        return routeService;
    }

    private void assertWebServiceAvailable() throws Exception {
        TestServiceRequest request = new TestServiceRequest();
        TestServiceRequestBody requestBody = new TestServiceRequestBody();
        TestServiceRequestRoot requestRoot = new TestServiceRequestRoot();
        requestBody.setTestServiceRequestRoot(requestRoot);
        request.setTestServiceRequestBody(requestBody);
        Response<TestServiceResponse> response = api.testService(request).execute();
        if (response.isSuccessful()) {
            TestServiceResponse testServiceResponse = response.body();
            if (testServiceResponse != null) {
                if (!"OK".equals(testServiceResponse.getResult())) {
                    throw new SoapServiceUnavailableException();
                }
            }
        }
    }

    private boolean sanityCheck(RouteXml routeXml) {
        return routeXml.getRouteID() != null &&
                routeXml.getRouteStart() != null &&
                routeXml.getRouteEnd() != null;
    }

    private static class Mapper {

        private static BusStop toBusStop(BusStopXml busStopXml) {
            return new BusStop(busStopXml.getId().trim(),
                    busStopXml.getAddress().trim(),
                    Double.parseDouble(busStopXml.getLatitude().trim()),
                    Double.parseDouble(busStopXml.getLongitude().trim()));
        }

        private static Route toRoute(RouteXml routeXml) {
            return new Route(routeXml.getRouteID().toUpperCase().trim(),
                    routeXml.getRouteStart().trim(),
                    routeXml.getRouteEnd().trim());
        }

        private static RealTimeData toRealTimeData(RealTimeDataXml realTimeDataXml) {
            return new RealTimeData(realTimeDataXml.getRouteID().toUpperCase().trim(),
                    realTimeDataXml.getDestination().trim(),
                    realTimeDataXml.getExpectedTime().trim(),
                    realTimeDataXml.getTimestamp().trim());
        }

        private static List<String> toInboundStopIds(List<RouteServiceInboundStopXml> xmls) {
            if (CollectionUtilities.isNullOrEmpty(xmls)) {
                return Collections.emptyList();
            }
            List<String> stopIds = new ArrayList<>();
            for (RouteServiceInboundStopXml xml : xmls) {
                stopIds.add(xml.getStopId());
            }
            return stopIds;
        }

        private static List<String> toOutboundStopIds(List<RouteServiceOutboundStopXml> xmls) {
            if (CollectionUtilities.isNullOrEmpty(xmls)) {
                return Collections.emptyList();
            }
            List<String> stopIds = new ArrayList<>();
            for (RouteServiceOutboundStopXml xml : xmls) {
                stopIds.add(xml.getStopId());
            }
            return stopIds;
        }

    }

}
