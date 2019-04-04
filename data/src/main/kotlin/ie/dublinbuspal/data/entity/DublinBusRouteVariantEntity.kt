package ie.dublinbuspal.data.entity

import androidx.room.*

@Entity(
        tableName = "route_variants",
        indices = [Index("route_id")],
        foreignKeys = [
            ForeignKey(
                    entity = DublinBusRouteInfoEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("route_id"),
                    onDelete = ForeignKey.CASCADE
            )
        ]
)
data class DublinBusRouteVariantEntity(
        @field:PrimaryKey(autoGenerate = true) @field:ColumnInfo(name = "id") val id: Int = 0,
        @field:ColumnInfo(name = "route_id") val routeId: String,
        @field:ColumnInfo(name = "origin") val origin: String,
        @field:ColumnInfo(name = "destination") val destination: String
)
