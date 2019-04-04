package ie.dublinbuspal.model.route

import ie.dublinbuspal.util.Operator

data class Route(
        val id: String,
        val variants: List<RouteVariant>,
        val origin: String = variants[0].origin,
        val destination: String = variants[0].destination,
        val operator: Operator
)

data class RouteVariant(
        val origin: String,
        val destination: String
)
