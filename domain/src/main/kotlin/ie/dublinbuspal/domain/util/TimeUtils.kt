package ie.dublinbuspal.domain.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

object TimeUtils {

    private val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.UK)

    fun parse(timestamp: String): LocalDateTime {
        return LocalDateTime.parse(timestamp, formatter)
    }

}