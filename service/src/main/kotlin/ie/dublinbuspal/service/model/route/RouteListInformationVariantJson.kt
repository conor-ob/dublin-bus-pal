package ie.dublinbuspal.service.model.route

import com.google.gson.annotations.SerializedName

data class RouteListInformationVariantJson(
        @SerializedName("origin") var origin: String? = null,
        @SerializedName("destination") var destination: String? = null
)
