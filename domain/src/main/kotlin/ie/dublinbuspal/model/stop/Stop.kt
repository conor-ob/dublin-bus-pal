package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.Coordinate

data class Stop(
        val id: String,
        val name: String,
        val coordinate: Coordinate
)
