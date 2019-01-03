package ie.dublinbuspal.service.api

import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import io.reactivex.Single
import retrofit2.http.GET

interface StaticApi {

    @GET("xml/default_stops.xml")
    fun getDefaultStops(): Single<StopsResponseXml>

    @GET("xml/default_routes.xml")
    fun getDefaultRoutes(): Single<RoutesResponseXml>

    @GET("json/dublin_bus_stops.json")
    fun getDublinBusStops(): Single<StopsResponseJson>

    @GET("json/go_ahead_dublin_stops.json")
    fun getGoAheadDublinStops(): Single<StopsResponseJson>

    @GET("json/go_ahead_dublin_routes.json")
    fun getGoAheadDublinRoutes(): Single<RouteListInformationWithVariantsResponseJson>

}
