package ie.dublinbuspal.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "route_services")
data class RouteServiceEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") var id: String = "", //TODO tidy this up
        @field:ColumnInfo(name = "name") val name: String,
        @field:ColumnInfo(name = "origin") val origin: String,
        @field:ColumnInfo(name = "destination") val destination: String,
        @field:ColumnInfo(name = "inbound_stops") val inboundStops: List<String>,
        @field:ColumnInfo(name = "outbound_stops") val outboundStops: List<String>
)
