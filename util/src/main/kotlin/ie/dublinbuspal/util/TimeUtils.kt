package ie.dublinbuspal.util

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

object TimeUtils {

    private val zoneId: ZoneId by lazy { ZoneId.of(TimeZone.getDefault().id) }
    private val zoneOffset: ZoneOffset by lazy { ZoneOffset.systemDefault().rules.getOffset(Instant.now()) }

    @JvmStatic
    fun now(): Instant {
        val instant = Instant.now()
        val zonedDateTime = instant.atZone(zoneId)
        return zonedDateTime.toInstant()
    }

    @JvmStatic
    fun dateTimeStampToInstant(timestamp: String, formatter: DateTimeFormatter): Instant {
        return LocalDateTime.parse(timestamp, formatter).toInstant(zoneOffset)
    }

    @JvmStatic
    fun secondsBetween(earlierInstant: Instant, laterInstant: Instant): Long {
        return ChronoUnit.SECONDS.between(earlierInstant, laterInstant)
    }

    @JvmStatic
    fun minutesBetween(earlierInstant: Instant, laterInstant: Instant): Long {
        return ChronoUnit.MINUTES.between(earlierInstant, laterInstant)
    }

    @JvmStatic
    fun formatAsTime(instant: Instant): String {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString()
    }

    @JvmStatic
    fun formatAsAge(instantNow: Instant, instantInThePast: Instant): String {
        val days = ChronoUnit.DAYS.between(instantInThePast, instantNow)
        if (days > 365) {
            val dateTime = LocalDateTime.ofInstant(instantInThePast, zoneId)
            return Formatter.dayMonthYear.format(dateTime)
        }
        val hours = ChronoUnit.HOURS.between(instantInThePast, instantNow)
        if (hours > 23) {
            val dateTime = LocalDateTime.ofInstant(instantInThePast, zoneId)
            return Formatter.dayMonth.format(dateTime)
        }
        if (hours >= 1) {
            return "${hours}h"
        }
        val minutes = minutesBetween(instantInThePast, instantNow)
        if (minutes > 1) {
            return "${minutes}m"
        }
        return "Just now"
    }

    @JvmStatic
    fun formatAsDate(instant: Instant): String {
        val dateTime = LocalDateTime.ofInstant(instant, zoneId)
        return Formatter.dayMonthTime.format(dateTime)
    }

}

object Formatter {

    val isoDateTime: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
    val dateTime: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val zonedDateTime: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss VV")
    val dayMonthYear: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val dayMonth: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val dayMonthTime: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM, HH:mm")

}
