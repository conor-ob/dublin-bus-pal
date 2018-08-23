package ie.dublinbuspal.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteStopEntity(
        @field:PrimaryKey @field:ColumnInfo(name = "id") val id: String,
        @field:ColumnInfo(name = "name") val name: String,
        @field:ColumnInfo(name = "routes") val routes: List<String>,
        @field:ColumnInfo(name = "order") val order: Int
)
