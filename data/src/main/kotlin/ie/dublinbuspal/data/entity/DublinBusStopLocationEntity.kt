package ie.dublinbuspal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stop_locations")
data class DublinBusStopLocationEntity(
    @field:PrimaryKey @field:ColumnInfo(name = "id") val id: String,
    @field:ColumnInfo(name = "name") val name: String,
    @field:ColumnInfo(name = "latitude") val latitude: Double,
    @field:ColumnInfo(name = "longitude") val longitude: Double
)
