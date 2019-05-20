package ie.dublinbuspal.model.route

import ie.dublinbuspal.util.Operator

data class Route(
        val id: String,
        val variants: List<RouteVariant>,
        val origin: String = variants.first().origin, //TODO
        val destination: String = variants.first().destination,
        val operator: Operator
)

data class RouteVariant(
        val origin: String,
        val destination: String
)
