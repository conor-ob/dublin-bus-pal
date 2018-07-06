package ie.dublinbuspal.domain.model

data class BusStop(
        val id: String,
        val name: String,
        val latitude: Double,
        val longitude: Double
)
