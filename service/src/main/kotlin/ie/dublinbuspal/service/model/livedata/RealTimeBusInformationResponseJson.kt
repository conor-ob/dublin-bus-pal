package ie.dublinbuspal.service.model.livedata

import com.google.gson.annotations.SerializedName

data class RealTimeBusInformationResponseJson(
        @SerializedName("errorcode") var errorCode: String? = null,
        @SerializedName("errormessage") var errorMessage: String? = null,
        @SerializedName("numberofresults") var resultsCount: Int? = null,
        @SerializedName("timestamp") var timestamp: String? = null,
        @SerializedName("results") var realTimeBusInformation: List<RealTimeBusInformationJson> = mutableListOf()
)
