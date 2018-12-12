package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.StringUtils

data class ResolvedStop(
        private var id: String? = null,
        private var bacId: String? = null,
        private var gadId: String? = null,

        private var favouriteName: String? = null,
        private var name: String? = null,
        private var bacName: String? = null,
        private var gadName: String? = null,

        private var coordinate: Coordinate? = null,
        private var bacCoordinate: Coordinate? = null,
        private var gadCoordinate: Coordinate? = null,

        private var favouriteRoutes: List<String> = emptyList(),
        private var routes: List<String> = emptyList(),
        private var bacRoutes: List<String> = emptyList(),
        private var gadRoutes: List<String> = emptyList()
) {

    fun id(): String {
        if (!StringUtils.isNullOrEmpty(id)) {
            return id!!
        } else if (!StringUtils.isNullOrEmpty(bacId)) {
            return bacId!!
        }
        return gadId!!
    }

    fun name(): String {
        if (!StringUtils.isNullOrEmpty(favouriteName)) {
            return favouriteName!!
        } else if (!StringUtils.isNullOrEmpty(name)) {
            return name!!
        } else if (!StringUtils.isNullOrEmpty(bacName)) {
            return bacName!!
        }
        return gadName!!
    }

    fun coordinate(): Coordinate {
        if (coordinate != null) {
            return coordinate!!
        } else if (bacCoordinate != null) {
            return bacCoordinate!!
        }
        return gadCoordinate!!
    }

    fun routes(): List<String> {
        if (!CollectionUtils.isNullOrEmpty(favouriteRoutes)) {
            return favouriteRoutes
        }
        val uniques = mutableSetOf<String>()
        uniques.addAll(routes)
        uniques.addAll(bacRoutes)
        uniques.addAll(gadRoutes)
        return uniques.toList()
    }

    fun isFavourite(): Boolean {
        return !StringUtils.isNullOrEmpty(favouriteName)
    }

    fun isBacOnly(): Boolean {
        return isBac() && !isGad()
    }

    fun isGadOnly(): Boolean {
        return isGad() && !isBac()
    }

    fun isBacAndGad(): Boolean {
        return isBac() && isGad()
    }

    private fun isBac(): Boolean {
        return !StringUtils.isNullOrEmpty(id) || !StringUtils.isNullOrEmpty(bacId)
    }

    private fun isGad(): Boolean {
        return !StringUtils.isNullOrEmpty(gadId)
    }

}
