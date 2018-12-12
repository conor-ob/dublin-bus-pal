package ie.dublinbuspal.service.model.route

import com.google.gson.annotations.SerializedName

data class RouteListInformationWithVariantsResponseJson(
        @SerializedName("errorcode") var errorCode: String? = null,
        @SerializedName("errormessage") var errorMessage: String? = null,
        @SerializedName("numberofresults") var resultsCount: Int? = null,
        @SerializedName("timestamp") var timestamp: String? = null,
        @SerializedName("results") var routes: List<RouteListInformationWithVariantsJson>? = null
)
