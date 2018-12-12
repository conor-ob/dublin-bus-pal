package ie.dublinbuspal.model.route

import ie.dublinbuspal.model.service.Operator

data class Route(
        val id: String,
        val variants: List<RouteVariant>,
        val operator: Operator
)
