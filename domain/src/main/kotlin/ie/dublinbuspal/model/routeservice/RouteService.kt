package ie.dublinbuspal.model.routeservice

data class RouteService(
        val id: String,
        val name: String,
        val origin: String,
        val destination: String,
        val inboundStops: List<String>,
        val outboundStops: List<String>
)
