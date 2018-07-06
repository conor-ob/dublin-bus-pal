package ie.dublinbuspal.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "tb_bus_stops")
data class BusStopEntity(
        @field:NonNull @field:PrimaryKey @field:ColumnInfo(name = "stop_id") val id: String,
        @field:ColumnInfo(name = "stop_name") val name: String,
        @field:ColumnInfo(name = "stop_latitude") val latitude: Double,
        @field:ColumnInfo(name = "stop_longitude") val longitude: Double
)
