package ie.dublinbuspal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "default_route_services")
data class DefaultRouteServiceEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") var id: String = "", //TODO tidy this up
        @field:ColumnInfo(name = "name") val name: String,
        @field:ColumnInfo(name = "origin") val origin: String,
        @field:ColumnInfo(name = "destination") val destination: String,
        @field:ColumnInfo(name = "inbound_stops") val inboundStops: List<String>,
        @field:ColumnInfo(name = "outbound_stops") val outboundStops: List<String>
)
