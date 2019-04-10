package ie.dublinbuspal.service.api.rtpi

import com.google.gson.annotations.SerializedName

data class RtpiRouteInformationResponseJson(
    @SerializedName("errorcode") val errorCode: String,
    @SerializedName("errormessage") val errorMessage: String,
    @SerializedName("numberofresults") val resultsCount: Int? = null,
    @SerializedName("timestamp") val timestamp: String? = null,
    @SerializedName("results") val results: List<RtpiRouteInformationJson> = mutableListOf()
)

data class RtpiRouteInformationJson(
    @SerializedName("operator") val operator: String,
    @SerializedName("origin") val origin: String,
    @SerializedName("originlocalized") val originLocalized: String? = null,
    @SerializedName("destination") val destination: String,
    @SerializedName("destinationlocalized") val destinationLocalized: String? = null,
    @SerializedName("lastupdated") val lastUpdated: String,
    @SerializedName("stops") val stops: List<RtpiBusStopInformationJson> = mutableListOf()
)

data class RtpiRouteService(
        val routeId: String,
        val operator: String,
        val variants: List<RtpiRouteServiceVariant>
)

data class RtpiRouteServiceVariant(
        val origin: String,
        val originLocalized: String? = null,
        val destination: String,
        val destinationLocalized: String? = null,
        val stopIds: List<String> = mutableListOf()
)
