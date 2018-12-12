package ie.dublinbuspal.model.livedata

import java.util.*

data class RealTimeBusInformation(
        val routeId: String,
        val destination: Destination,
        val dueTime: DueTime
) {

    fun destinationHashCode(): Int {
        return Objects.hash(routeId, destination)
    }

}
