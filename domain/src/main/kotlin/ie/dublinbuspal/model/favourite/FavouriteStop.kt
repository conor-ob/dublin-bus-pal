package ie.dublinbuspal.model.favourite

import ie.dublinbuspal.util.AlphanumComparator

data class FavouriteStop(
        val id: String,
        val name: String,
        val routes: Set<String>,
        val order: Int
) {

        fun routes(): List<String> {
                val routes = routes.toMutableList()
                routes.sortWith(Comparator { thisRoute, thatRoute -> AlphanumComparator.getInstance().compare(thisRoute, thatRoute) } )
                return routes
        }

}
