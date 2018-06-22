package ie.dublinbuspal.android.data.remote.rest;

import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;
import ie.dublinbuspal.android.data.remote.rest.json.stop.BusStopInformation;
import ie.dublinbuspal.android.data.remote.rest.json.stop.BusStopResult;
import ie.dublinbuspal.android.data.remote.rest.json.stop.Operator;
import ie.dublinbuspal.android.data.remote.rest.json.timetable.TimetableInformation;
import ie.dublinbuspal.android.util.AlphanumComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Response;

public class DublinBusRestServiceAdapter implements RestDataSource {

    private static final String OPERATOR = "bac";
    private static final String FORMAT = "json";
    private static final String TYPE = "day";

    private final DublinBusRestApi api;

    public DublinBusRestServiceAdapter(DublinBusRestApi api) {
        this.api = api;
    }

    @Override
    public List<UncheckedBusStopService> getUncheckedBusStopServices() throws Exception {
        List<UncheckedBusStopService> busStopServices = new ArrayList<>();
        Response<BusStopInformation> response = api.getBusStopInformation(OPERATOR, FORMAT)
                .execute();
        if (response.isSuccessful()) {
            BusStopInformation body = response.body();
            if (body != null) {
                List<BusStopResult> busStopResults = body.getBusStopResults();
                for (BusStopResult busStopResult : busStopResults) {
                    List<Operator> operators = busStopResult.getOperators();
                    if (operators.size() == 1) {
                        Operator operator = operators.get(0);
                        Set<String> removeDupes = new HashSet<>();
                        removeDupes.addAll(operator.getRoutes());
                        List<String> routes = new ArrayList<>();
                        routes.addAll(removeDupes);
                        Collections.sort(routes, AlphanumComparator.getInstance());
                        busStopServices.add(new UncheckedBusStopService(busStopResult
                                .getDisplaystopid(), routes));
                    }
                }
            }
        }
        return busStopServices;
    }

    public Object getTimetable(String stopId, String routeId) throws Exception {
        Response<TimetableInformation> response = api.getTimetableInformation(TYPE, stopId, routeId,
                OPERATOR, FORMAT).execute();
        if (response.isSuccessful()) {
            TimetableInformation timetableInformation = response.body();
        }
        return null;
    }

}
