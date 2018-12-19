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
    fun fromList(list: List<String>): String? {
        if (CollectionUtils.isNullOrEmpty(list)) {
            return null //TODO
        }
        return StringUtils.join(list, DELIMETER)
    }

    @TypeConverter
    @JvmStatic
    fun toList(value: String?): List<String> {
        if (value != null) {
            return value.split(DELIMETER)
        }
        return Collections.emptyList()
    }

    @TypeConverter
    @JvmStatic
    fun fromInstant(instant: Instant?): Long? {
        if (instant == null) {
            return 0L
        }
        return instant.epochSecond
    }

    @TypeConverter
    @JvmStatic
    fun toInstant(value: Long?): Instant {
        if (value == null) {
            return Instant.ofEpochSecond(0L)
        }
        return Instant.ofEpochSecond(value)
    }

}
