package ie.dublinbuspal.android.data.remote.rest;

import ie.dublinbuspal.android.data.local.entity.UncheckedBusStopService;

import java.util.List;

public interface RestDataSource {

    List<UncheckedBusStopService> getUncheckedBusStopServices() throws Exception;

}
