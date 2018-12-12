package ie.dublinbuspal.service.model.stop

import com.google.gson.annotations.SerializedName

data class StopsResponseJson(
        @SerializedName("errorcode") var errorCode: String? = null,
        @SerializedName("errormessage") var errorMessage: String? = null,
        @SerializedName("numberofresults") var resultsCount: Int? = null,
        @SerializedName("timestamp") var timestamp: String? = null,
        @SerializedName("results") var stops: List<StopJson>? = null
)
