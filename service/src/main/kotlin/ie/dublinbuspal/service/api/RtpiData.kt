package ie.dublinbuspal.service.api

data class RtpiLiveData(
        val routeId: String,
        val operatorId: String,
        val destination: String,
        val expectedTimestamp: String
)

data class RtpiStop(
        val id: String,
        val name: String,
        val latitude: String,
        val longitude: String,
        val stopServices: List<StopService>
)

data class StopService(
        val operatorId: String,
        val routeIds: List<String>
)

data class RtpiRoute(
        val routeId: String,
        val operatorId: String,
        val variants: List<RouteVariant>
)

data class RouteVariant(
        val origin: String,
        val destination: String
)

data class RtpiRouteService(
        val routeId: String,
        val operatorId: String,
        val variants: List<RtpiRouteServiceVariant>
)

data class RtpiRouteServiceVariant(
        val origin: String,
        val destination: String,
        val stopIds: List<String> = mutableListOf()
)
