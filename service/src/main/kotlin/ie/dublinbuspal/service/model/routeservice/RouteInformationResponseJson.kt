package ie.dublinbuspal.service.model.routeservice

import com.google.gson.annotations.SerializedName

data class RouteInformationResponseJson(
        @SerializedName("errorcode") var errorCode: String? = null,
        @SerializedName("errormessage") var errorMessage: String? = null,
        @SerializedName("numberofresults") var resultsCount: Int? = null,
        @SerializedName("timestamp") var timestamp: String? = null,
        @SerializedName("results") var results: List<RouteInformationJson> = mutableListOf()
)
