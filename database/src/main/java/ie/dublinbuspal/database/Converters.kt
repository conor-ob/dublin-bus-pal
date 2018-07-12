package ie.dublinbuspal.database

import android.arch.persistence.room.TypeConverter
import ie.dublinbuspal.base.util.CollectionUtils
import ie.dublinbuspal.base.util.StringUtils
import java.util.*

object Converters {

    private const val DELIMETER = "::"

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<String>): String? {
        if (CollectionUtils.isNullOrEmpty(list)) {
            return null //TODO
        }
        return StringUtils.join(list, DELIMETER)
    }

    @TypeConverter
    @JvmStatic
    fun toList(value: String): List<String> {
        if (value != null) {
            return value.split(DELIMETER)
        }
        return Collections.emptyList()
    }

}
