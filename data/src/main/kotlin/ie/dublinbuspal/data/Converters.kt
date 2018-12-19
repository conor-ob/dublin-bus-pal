package ie.dublinbuspal.data

import androidx.room.TypeConverter
import ie.dublinbuspal.util.CollectionUtils
import ie.dublinbuspal.util.StringUtils
import org.threeten.bp.Instant
import java.util.*

object Converters {

    private const val DELIMETER = "::"

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<String>): String {
        if (CollectionUtils.isNullOrEmpty(list)) {
            return DELIMETER
        }
        return StringUtils.join(list, DELIMETER)
    }

    @TypeConverter
    @JvmStatic
    fun toList(value: String): List<String> {
        if (value == DELIMETER) {
            return Collections.emptyList()
        }
        return value.split(DELIMETER)
    }

    @TypeConverter
    @JvmStatic
    fun fromInstant(instant: Instant): Long {
        return instant.epochSecond
    }

    @TypeConverter
    @JvmStatic
    fun toInstant(value: Long): Instant {
        return Instant.ofEpochSecond(value)
    }

}
