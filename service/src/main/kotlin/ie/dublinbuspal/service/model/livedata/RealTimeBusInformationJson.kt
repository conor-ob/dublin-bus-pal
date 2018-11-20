package ie.dublinbuspal.service.model.livedata

import com.google.gson.annotations.SerializedName

data class RealTimeBusInformationJson(
        @SerializedName("route") var route: String? = null,
        @SerializedName("operator") var operator: String? = null,
        @SerializedName("destination") var destination: String? = null,
        @SerializedName("direction") var direction: String? = null,
        @SerializedName("duetime") var duetime: String? = null,
        @SerializedName("arrivaldatetime") var expectedTime: String? = null
)
