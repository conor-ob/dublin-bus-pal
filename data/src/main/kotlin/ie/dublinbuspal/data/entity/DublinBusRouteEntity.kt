package ie.dublinbuspal.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DublinBusRouteEntity(
        @field:Embedded val info: DublinBusRouteInfoEntity
) {
    @field:Relation(parentColumn = "id", entityColumn = "route_id")
    var variants: List<DublinBusRouteVariantEntity> = emptyList()
}
