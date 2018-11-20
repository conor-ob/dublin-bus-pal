package ie.dublinbuspal.model.livedata

import java.util.*

data class RealTimeStopData(
        val routeId: String,
        val destination: String,
        val dueTime: DueTime
) {

    fun destinationHashCode(): Int {
        return Objects.hash(routeId, destination)
    }

}
