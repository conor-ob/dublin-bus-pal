package ie.dublinbuspal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routes")
data class DublinBusRouteInfoEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") val id: String,
        @field:ColumnInfo(name = "operator") val operator: String
)
