package ie.dublinbuspal.service

import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.status.ServiceStatusRequestXml
import ie.dublinbuspal.service.model.status.ServiceStatusResponseXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DublinBusApi {

    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/DublinBusRTPIService.asmx")
    fun getBusStops(@Body body: StopsRequestXml): Single<StopsResponseXml>

    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/DublinBusRTPIService.asmx")
    fun getRoutes(@Body body: RoutesRequestXml): Single<RoutesResponseXml>

    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/DublinBusRTPIService.asmx")
    fun getLiveData(@Body body: LiveDataRequestXml): Single<LiveDataResponseXml>

    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/DublinBusRTPIService.asmx")
    fun getStopService(@Body body: StopServiceRequestXml): Single<StopServiceResponseXml>

    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/DublinBusRTPIService.asmx")
    fun getRouteService(@Body body: RouteServiceRequestXml): Single<RouteServiceResponseXml>

    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/DublinBusRTPIService.asmx")
    fun getServiceStatus(@Body body: ServiceStatusRequestXml): Single<ServiceStatusResponseXml>
    
}
