package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.Coordinate

data class DublinBusStop(
        val id: String,
        val name: String,
        val coordinate: Coordinate,
        val routes: List<String>
)
