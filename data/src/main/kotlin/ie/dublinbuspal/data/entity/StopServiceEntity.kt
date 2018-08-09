package ie.dublinbuspal.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "stop_services")
data class StopServiceEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") var id: String = "", //TODO tidy this up
        @field:ColumnInfo(name = "routes") val routes: List<String>
)
