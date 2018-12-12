package ie.dublinbuspal.service.model.stop

import com.google.gson.annotations.SerializedName

data class OperatorJson(
        @SerializedName("name") var name: String? = null,
        @SerializedName("routes") var routes: List<String>? = null
)
