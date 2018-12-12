package ie.dublinbuspal.model.route

data class GoAheadDublinRoute(
        val id: String,
        val variants: List<RouteVariant>
)