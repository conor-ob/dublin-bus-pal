package ie.dublinbuspal.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "stops")
data class StopEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") val id: String,
        @field:ColumnInfo(name = "name") val name: String,
        @field:ColumnInfo(name = "latitude") val latitude: Double,
        @field:ColumnInfo(name = "longitude") val longitude: Double
)
