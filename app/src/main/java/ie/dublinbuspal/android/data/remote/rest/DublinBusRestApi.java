package ie.dublinbuspal.android.data.remote.rest;

import ie.dublinbuspal.android.data.remote.rest.json.stop.BusStopInformation;
import ie.dublinbuspal.android.data.remote.rest.json.timetable.TimetableInformation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface DublinBusRestApi {

    String BASE_URL = "https://data.dublinked.ie/cgi-bin/rtpi/";

    @Streaming
    @GET("busstopinformation")
    Call<BusStopInformation> getBusStopInformation(@Query("operator") String operator,
                                                   @Query("format") String format);

    @GET
    Call<TimetableInformation> getTimetableInformation(@Query("type") String type,
                                                       @Query("stopid") String stopId,
                                                       @Query("routeid") String routeId,
                                                       @Query("operator") String operator,
                                                       @Query("format") String format);

}
