package ie.dublinbuspal.service

import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.stop.BusStopsRequestXml
import ie.dublinbuspal.service.model.stop.BusStopsResponseXml
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DublinBusApi {

    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/DublinBusRTPIService.asmx")
    fun getBusStops(@Body body: BusStopsRequestXml): Single<BusStopsResponseXml>

//    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
//    @POST("/DublinBusRTPIService.asmx")
//    fun getRoutes(@Body body: RoutesRequestXml): Single<RoutesResponseXml>

    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/DublinBusRTPIService.asmx")
    fun getLiveData(@Body body: LiveDataRequestXml): Single<LiveDataResponseXml>

//    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
//    @POST("/DublinBusRTPIService.asmx")
//    fun getBusStopService(@Body body: BusStopServiceRequestXml): Single<BusStopServiceResponseXml>

//    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
//    @POST("/DublinBusRTPIService.asmx")
//    fun getRouteService(@Body body: RouteServiceRequestXml): Single<RouteServiceResponseXml>

//    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
//    @POST("/DublinBusRTPIService.asmx")
//    fun testService(@Body body: TestServiceRequestXml): Single<TestServiceResponseXml>
    
}
