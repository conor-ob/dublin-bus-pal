package ie.dublinbuspal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "go_ahead_dublin_routes")
data class GoAheadDublinRouteEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") val id: String,
        @field:ColumnInfo(name = "origin") val origin: String,
        @field:ColumnInfo(name = "destination") val destination: String
)
