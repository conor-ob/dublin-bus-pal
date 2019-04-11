package ie.dublinbuspal.service.api.rtpi

import com.google.gson.annotations.SerializedName

data class RtpiRouteInformationResponseJson(
    @SerializedName("errorcode") val errorCode: String? = null,
    @SerializedName("errormessage") val errorMessage: String,
    @SerializedName("numberofresults") val resultsCount: Int? = null,
    @SerializedName("timestamp") val timestamp: String? = null,
    @SerializedName("results") val results: List<RtpiRouteInformationJson> = mutableListOf()
)

data class RtpiRouteInformationJson(
    @SerializedName("operator") val operator: String? = null,
    @SerializedName("origin") val origin: String? = null,
    @SerializedName("originlocalized") val originLocalized: String? = null,
    @SerializedName("destination") val destination: String? = null,
    @SerializedName("destinationlocalized") val destinationLocalized: String? = null,
    @SerializedName("lastupdated") val lastUpdated: String? = null,
    @SerializedName("stops") val stops: List<RtpiBusStopInformationJson> = mutableListOf()
)
