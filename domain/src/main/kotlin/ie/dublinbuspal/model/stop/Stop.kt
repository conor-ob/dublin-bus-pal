package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.StringUtils

data class Stop(
        private var defaultId: String? = null,
        private var dublinBusId: String? = null,
        private var goAheadDublinId: String? = null,

        private var favouriteName: String? = null,
        private var defaultName: String? = null,
        private var dublinBusName: String? = null,
        private var goAheadDublinName: String? = null,

        private var defaultCoordinate: Coordinate? = null,
        private var dublinBusCoordinate: Coordinate? = null,
        private var goAheadDublinCoordinate: Coordinate? = null,

        private var favouriteRoutes: List<String> = emptyList(),
        private var defaultRoutes: List<String> = emptyList(),
        private var dublinBusRoutes: List<String> = emptyList(),
        private var goAheadDublinRoutes: List<String> = emptyList()
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
            return favouriteRoutes
        }
        val uniques = mutableSetOf<String>()
        uniques.addAll(defaultRoutes)
        uniques.addAll(dublinBusRoutes)
        uniques.addAll(goAheadDublinRoutes)
        return uniques.toList()
    }

    fun isFavourite(): Boolean {
        return !StringUtils.isNullOrEmpty(favouriteName)
    }

    fun isDublinBusOnly(): Boolean {
        return isDublinBus() && !isGoAheadDublin()
    }

    fun isGoAheadDublinOnly(): Boolean {
        return isGoAheadDublin() && !isDublinBus()
    }

    fun isDublinBusAndGoAheadDublin(): Boolean {
        return isDublinBus() && isGoAheadDublin()
    }

    private fun isDublinBus(): Boolean {
        return !StringUtils.isNullOrEmpty(defaultId) || !StringUtils.isNullOrEmpty(dublinBusId)
    }

    private fun isGoAheadDublin(): Boolean {
        return !StringUtils.isNullOrEmpty(goAheadDublinId)
    }

}
