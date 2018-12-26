package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.Coordinate

data class GoAheadDublinStop(
        val id: String,
        val name: String,
        val coordinate: Coordinate,
        val routes: List<String>
)
