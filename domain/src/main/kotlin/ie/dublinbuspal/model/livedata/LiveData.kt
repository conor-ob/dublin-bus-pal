package ie.dublinbuspal.model.livedata

import ie.dublinbuspal.util.Operator
import java.util.*

data class LiveData(
        val routeId: String,
        val operator: Operator,
        val destination: Destination,
        val dueTime: DueTime
) {

    fun destinationHashCode(): Int {
        return Objects.hash(routeId, destination)
    }

}
