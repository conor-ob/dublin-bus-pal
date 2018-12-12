package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.Coordinate

data class SmartDublinStop(
        val id: String,
        val name: String,
        val coordinate: Coordinate,
        val routes: List<String>
)
