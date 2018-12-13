package ie.dublinbuspal.service.api

import ie.dublinbuspal.service.model.livedata.RealTimeBusInformationResponseJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DublinBusGoAheadDublinRestApi {

    @GET("busstopinformation")
    fun getStops(@Query("operator") operator: String,
                 @Query("format") format: String): Single<StopsResponseJson>

    @GET("routelistinformation/withvariants")
    fun getRoutes(@Query("format") format: String): Single<RouteListInformationWithVariantsResponseJson>

    @GET("realtimebusinformation")
    fun getLiveData(@Query("stopid") id: String,
                    @Query("operator") operator: String,
                    @Query("format") format: String): Single<RealTimeBusInformationResponseJson>

}
