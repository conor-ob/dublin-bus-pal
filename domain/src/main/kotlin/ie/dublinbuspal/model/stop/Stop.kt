package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.AlphanumComparator
import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.StringUtils

data class Stop(
        private val defaultId: String? = null,
        private val dublinBusId: String? = null,
        private val goAheadDublinId: String? = null,

        private val favouriteName: String? = null,
        private val defaultName: String? = null,
        private val dublinBusName: String? = null,
        private val goAheadDublinName: String? = null,

        private val defaultCoordinate: Coordinate? = null,
        private val dublinBusCoordinate: Coordinate? = null,
        private val goAheadDublinCoordinate: Coordinate? = null,

        private val favouriteRoutes: List<String> = emptyList(),
        private val defaultRoutes: List<String> = emptyList(),
        private val dublinBusRoutes: List<String> = emptyList(),
        private val goAheadDublinRoutes: List<String> = emptyList()
) {

    fun id(): String {
        if (!StringUtils.isNullOrEmpty(defaultId)) {
            return defaultId!!
        } else if (!StringUtils.isNullOrEmpty(dublinBusId)) {
            return dublinBusId!!
        }
        return goAheadDublinId!!
    }

    fun name(): String {
        if (!StringUtils.isNullOrEmpty(favouriteName)) {
            return favouriteName!!
        } else if (!StringUtils.isNullOrEmpty(defaultName)) {
            return defaultName!!
        } else if (!StringUtils.isNullOrEmpty(dublinBusName)) {
            return dublinBusName!!
        }
        return goAheadDublinName!!
    }

    fun coordinate(): Coordinate {
        if (defaultCoordinate != null) {
            return defaultCoordinate!!
        } else if (dublinBusCoordinate != null) {
            return dublinBusCoordinate!!
        }
        return goAheadDublinCoordinate!!
    }

    fun routes(): List<String> {
        if (!CollectionUtils.isNullOrEmpty(favouriteRoutes)) {
            val routes = favouriteRoutes.toMutableList()
            routes.sortWith(Comparator { thisRoute, thatRoute -> AlphanumComparator.getInstance().compare(thisRoute, thatRoute) } )
            return routes
        }
        val uniques = mutableSetOf<String>()
        uniques.addAll(defaultRoutes)
        uniques.addAll(dublinBusRoutes)
        uniques.addAll(goAheadDublinRoutes)
        val routes = uniques.toMutableList()
        routes.sortWith(Comparator { thisRoute, thatRoute -> AlphanumComparator.getInstance().compare(thisRoute, thatRoute) } )
        return routes
    }

    fun isFavourite(): Boolean {
        return StringUtils.isNotNullOrEmpty(favouriteName)
    }

    fun isDublinBusOnly(): Boolean {
        return (isDefault() || isDublinBus()) && !isGoAheadDublin()
    }

    fun isGoAheadDublinOnly(): Boolean {
        return !isDefault() && !isDublinBus() && isGoAheadDublin()
    }

    fun isDublinBusAndGoAheadDublin(): Boolean {
        return (isDefault() || isDublinBus()) && isGoAheadDublin()
    }

    private fun isDefault(): Boolean {
        return StringUtils.isNotNullOrEmpty(defaultId)
    }

    private fun isDublinBus(): Boolean {
        return StringUtils.isNotNullOrEmpty(dublinBusId)
    }

    private fun isGoAheadDublin(): Boolean {
        return StringUtils.isNotNullOrEmpty(goAheadDublinId)
    }

}
