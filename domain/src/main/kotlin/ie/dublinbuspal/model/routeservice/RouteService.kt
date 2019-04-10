package ie.dublinbuspal.model.routeservice

import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.util.Operator

data class RouteService(
        val id: String,
        val operator: Operator,
        val variants: List<RouteServiceVariant>
)

data class RouteServiceVariant(
        val origin: String,
        val destination: String,
        val stops: List<Stop>
)
