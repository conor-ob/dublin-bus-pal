package ie.dublinbuspal.data.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import ie.dublinbuspal.data.entity.DublinBusStopLocationEntity

@Entity(
    tableName = "stop_services",
    indices = [Index("stop_id")],
    foreignKeys = [
        ForeignKey(
            entity = DublinBusStopLocationEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("stop_id"),
            onDelete = CASCADE
        )
    ]
)
data class DublinBusStopServiceEntity(
    @field:PrimaryKey(autoGenerate = true) @field:ColumnInfo(name = "id") val id: Int = 0,
    @field:ColumnInfo(name = "stop_id") val stopId: String,
    @field:ColumnInfo(name = "operator") val operator: String,
    @field:ColumnInfo(name = "route") val route: String
)
