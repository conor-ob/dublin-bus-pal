package ie.dublinbuspal.model.routeservice

import ie.dublinbuspal.model.stop.Stop

data class RouteService(
        val id: String,
        val origin: String,
        val destination: String,
        val inboundStopIds: List<String>,
        val outboundStopIds: List<String>,
        val inboundStops: List<Stop>,
        val outboundStops: List<Stop>
)
