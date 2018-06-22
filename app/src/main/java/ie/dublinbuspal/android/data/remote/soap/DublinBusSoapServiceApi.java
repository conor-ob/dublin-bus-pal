package ie.dublinbuspal.android.data.remote.soap;

import ie.dublinbuspal.android.data.remote.soap.xml.realtime.RealTimeDataRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.realtime.RealTimeDataResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RouteServiceResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RoutesRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.route.RoutesResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopServiceRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopServiceResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopsRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.stop.BusStopsResponse;
import ie.dublinbuspal.android.data.remote.soap.xml.test.TestServiceRequest;
import ie.dublinbuspal.android.data.remote.soap.xml.test.TestServiceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DublinBusSoapServiceApi {

    String BASE_URL = "http://rtpi.dublinbus.ie";

    @Headers({"Content-Type: text/xml", "Accept-Charset: utf-8"})
    @POST("/DublinBusRTPIService.asmx")
    Call<BusStopsResponse> getBusStops(@Body BusStopsRequest body);

    @Headers({"Content-Type: text/xml", "Accept-Charset: utf-8"})
    @POST("/DublinBusRTPIService.asmx")
    Call<RoutesResponse> getRoutes(@Body RoutesRequest body);

    @Headers({"Content-Type: text/xml", "Accept-Charset: utf-8"})
    @POST("/DublinBusRTPIService.asmx")
    Call<RealTimeDataResponse> getRealTimeData(@Body RealTimeDataRequest body);

    @Headers({"Content-Type: text/xml", "Accept-Charset: utf-8"})
    @POST("/DublinBusRTPIService.asmx")
    Call<BusStopServiceResponse> getBusStopService(@Body BusStopServiceRequest body);

    @Headers({"Content-Type: text/xml", "Accept-Charset: utf-8"})
    @POST("/DublinBusRTPIService.asmx")
    Call<RouteServiceResponse> getRouteService(@Body RouteServiceRequest body);

    @Headers({"Content-Type: text/xml", "Accept-Charset: utf-8"})
    @POST("/DublinBusRTPIService.asmx")
    Call<TestServiceResponse> testService(@Body TestServiceRequest body);

}
