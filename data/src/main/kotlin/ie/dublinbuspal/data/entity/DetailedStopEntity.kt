package ie.dublinbuspal.data.entity

data class DetailedStopEntity(
        val id: String,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        var favouriteName: String? = null,
        var favouriteOrder: Int? = null,
        var routes: List<String>? = emptyList(),
        var smartDublinRoutes: List<String>? = emptyList(),
        var favouriteRoutes: List<String>? = emptyList()
) {

    fun customName(): String? {
        if (favouriteName != null) {
            return favouriteName
        }
        return name
    }

    fun customRoutes(): List<String>? {
        if (favouriteRoutes != null && favouriteRoutes!!.isNotEmpty()) {
            return favouriteRoutes
        } else if (routes != null && routes!!.isNotEmpty()) {
            return routes
        }
        return smartDublinRoutes
    }

}
