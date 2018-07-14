package ie.dublinbuspal.domain.model.livedata

data class LiveData(
        val routeId: String,
        val destination: String,
        val dueTime: DueTime
)
