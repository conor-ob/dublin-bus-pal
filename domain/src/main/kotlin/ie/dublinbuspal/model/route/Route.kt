package ie.dublinbuspal.model.route

import ie.dublinbuspal.util.Operator

data class Route(
        val id: String,
        val origin: String,
        val destination: String,
        val operator: Operator
)
