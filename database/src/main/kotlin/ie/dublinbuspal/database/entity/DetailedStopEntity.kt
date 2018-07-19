package ie.dublinbuspal.database.entity

data class DetailedStopEntity(
        val id: String,
        val name: String,
        var favouriteName: String? = null,
        val latitude: Double,
        val longitude: Double,
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

    fun isFavourite() = favouriteName != null

}
