package ie.dublinbuspal.model.livedata

import java.util.*

data class LiveData(
        val routeId: String,
        val destination: String,
        val dueTime: DueTime
) {

    fun destinationHashCode(): Int {
        return Objects.hash(routeId, destination)
    }

}
