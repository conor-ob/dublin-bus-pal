package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.*
import java.util.*

data class Stop(
        val id: String,
        val name: String,
        val favouriteName: String? = null,
        val coordinate: Coordinate,
        val operators: EnumSet<Operator>,
        val routes: Map<Operator, Set<String>>,
        val favouriteRoutes: Set<String> = emptySet()
) {

    fun id(): String {
        return id
    }

    fun name(): String {
        if (!StringUtils.isNullOrEmpty(favouriteName)) {
            return favouriteName!!
        }
        return name
    }

    fun coordinate(): Coordinate {
        return coordinate
    }

    fun routes(): List<String> {
        if (!CollectionUtils.isNullOrEmpty(favouriteRoutes)) {
            val routes = favouriteRoutes.toMutableList()
            routes.sortWith(Comparator { thisRoute, thatRoute -> AlphanumComparator.getInstance().compare(thisRoute, thatRoute) } )
            return routes
        }
        val uniques = mutableSetOf<String>()
        uniques.addAll(routes.values.flatten())
        val routes = uniques.toMutableList()
        routes.sortWith(Comparator { thisRoute, thatRoute -> AlphanumComparator.getInstance().compare(thisRoute, thatRoute) } )
        return routes
    }

    fun isFavourite(): Boolean {
        return StringUtils.isNotNullOrEmpty(favouriteName)
    }

    fun isDublinBusOnly(): Boolean {
        return isDublinBus() && !isGoAheadDublin()
    }

    fun isGoAheadDublinOnly(): Boolean {
        return !isDublinBus() && isGoAheadDublin()
    }

    fun isDublinBusAndGoAheadDublin(): Boolean {
        return isDublinBus() && isGoAheadDublin()
    }

    private fun isDublinBus(): Boolean {
        return operators.contains(Operator.DUBLIN_BUS)
    }

    private fun isGoAheadDublin(): Boolean {
        return operators.contains(Operator.GO_AHEAD)
    }

}
