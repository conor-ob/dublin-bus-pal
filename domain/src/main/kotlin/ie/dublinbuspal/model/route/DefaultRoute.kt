package ie.dublinbuspal.model.route

import ie.dublinbuspal.util.Operator

data class DefaultRoute(
        val id: String,
        val origin: String,
        val destination: String,
        val operator: Operator = Operator.DUBLIN_BUS
)
