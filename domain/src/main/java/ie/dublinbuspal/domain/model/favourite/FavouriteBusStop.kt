package ie.dublinbuspal.domain.model.favourite

data class FavouriteBusStop(
        val id: String,
        val name: String,
        val routes: List<String>,
        val order: Int
)
