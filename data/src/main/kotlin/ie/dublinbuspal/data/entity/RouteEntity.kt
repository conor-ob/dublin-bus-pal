package ie.dublinbuspal.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "routes")
data class RouteEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") val id: String,
        @field:ColumnInfo(name = "origin") val origin: String,
        @field:ColumnInfo(name = "destination") val destination: String
)
