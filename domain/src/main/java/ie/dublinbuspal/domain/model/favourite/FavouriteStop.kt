package ie.dublinbuspal.domain.model.favourite

data class FavouriteStop(
        val id: String,
        val name: String,
        val routes: List<String>,
        val order: Int
)
