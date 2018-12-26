package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.Coordinate

data class DefaultStop(
        val id: String,
        val name: String,
        val coordinate: Coordinate
)
