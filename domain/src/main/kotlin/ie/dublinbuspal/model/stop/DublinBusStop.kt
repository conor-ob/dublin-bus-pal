package ie.dublinbuspal.model.stop

import ie.dublinbuspal.util.Coordinate
import ie.dublinbuspal.util.Operator
import java.util.*

data class DublinBusStop(
        val id: String,
        val name: String,
        val coordinate: Coordinate,
        val operators: EnumSet<Operator>,
        val routes: Map<Operator, Set<String>>
)
