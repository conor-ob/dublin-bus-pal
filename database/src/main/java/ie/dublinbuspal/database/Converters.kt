package ie.dublinbuspal.database

import android.arch.persistence.room.TypeConverter

object Converters {

    private const val DELIMETER = "::"

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<String>): String {
        return "" //TODO
    }

    @TypeConverter
    @JvmStatic
    fun toList(value: String): List<String> {
        return emptyList() //TODO
    }

}
