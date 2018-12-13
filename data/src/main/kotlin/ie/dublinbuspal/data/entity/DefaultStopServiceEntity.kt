package ie.dublinbuspal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "default_stop_services")
data class DefaultStopServiceEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") var id: String = "", //TODO tidy this up
        @field:ColumnInfo(name = "routes") val routes: List<String>
)
