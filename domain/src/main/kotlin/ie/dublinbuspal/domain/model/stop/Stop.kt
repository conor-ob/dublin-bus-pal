package ie.dublinbuspal.domain.model.stop

import ie.dublinbuspal.base.Coordinate

data class Stop(
        val id: String,
        val name: String,
        val coordinate: Coordinate,
        val routes: List<String>
)
