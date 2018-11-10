package ie.dublinbuspal.service

import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SmartDublinRestApi {

    @GET("busstopinformation")
    fun getStops(@Query("operator") operator: String,
                 @Query("format") format: String) : Single<StopsResponseJson>

}