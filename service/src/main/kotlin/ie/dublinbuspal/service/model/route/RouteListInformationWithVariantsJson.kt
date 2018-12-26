package ie.dublinbuspal.service.model.route

import com.google.gson.annotations.SerializedName

data class RouteListInformationWithVariantsJson(
        @SerializedName("operator") var operator: String? = null,
        @SerializedName("route") var route: String? = null,
        @SerializedName("Variants") var variants: List<RouteListInformationVariantJson>? = null
)
