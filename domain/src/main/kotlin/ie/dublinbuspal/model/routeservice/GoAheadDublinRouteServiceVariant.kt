package ie.dublinbuspal.model.routeservice

import ie.dublinbuspal.model.stop.Stop

data class GoAheadDublinRouteServiceVariant(
        val origin: String,
        val destination: String,
        val stops: List<Stop>
)
