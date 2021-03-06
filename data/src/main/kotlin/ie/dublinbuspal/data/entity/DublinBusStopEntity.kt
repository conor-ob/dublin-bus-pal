package ie.dublinbuspal.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DublinBusStopEntity(
    @field:Embedded val location: DublinBusStopLocationEntity
) {
    @field:Relation(parentColumn = "id", entityColumn = "stop_id")
    var services: List<DublinBusStopServiceEntity> = emptyList()
}
