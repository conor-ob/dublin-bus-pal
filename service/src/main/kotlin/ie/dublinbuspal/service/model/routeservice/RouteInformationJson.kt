package ie.dublinbuspal.service.model.routeservice

import com.google.gson.annotations.SerializedName
import ie.dublinbuspal.service.model.stop.StopJson

data class RouteInformationJson(
        @SerializedName("origin") var origin: String? = null,
        @SerializedName("destination") var destination: String? = null,
        @SerializedName("stops") var stops: List<StopJson> = mutableListOf()
)
