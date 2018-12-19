package ie.dublinbuspal.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

object TimeUtils {

    private val logger: Logger by lazy { LoggerFactory.getLogger(javaClass) }
    private val zoneId: ZoneId by lazy { ZoneId.of(TimeZone.getDefault().id) }
    private val zoneOffset: ZoneOffset by lazy { ZoneOffset.systemDefault().rules.getOffset(Instant.now()) }
    private val parsers: List<DateTimeFormatter> by lazy {
        listOf(
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
                DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss VV")
        )
    }
    private val formatters: List<DateTimeFormatter> by lazy {
        listOf(
                DateTimeFormatter.ofPattern("dd MMM yyyy"),
                DateTimeFormatter.ofPattern("dd MMM")
        )
    }

    @JvmStatic
    fun now(): Instant {
        val instant = Instant.now()
        val zonedDateTime = instant.atZone(zoneId)
        return zonedDateTime.toInstant()
    }

    @JvmStatic
    fun toInstant(timestamp: String): Instant {
        for (parser in parsers) {
            try {
                return LocalDateTime.parse(timestamp, parser).toInstant(zoneOffset)
            } catch (e: DateTimeParseException) {
                logger.debug("parser [$parser] failed to parse timestamp [$timestamp]")
            }
        }
        throw Exception("Unable to parse timestamp [$timestamp]")
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
    fun toStringHoursMinutes(instant: Instant): String {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString()
    }

    @JvmStatic
    fun toString(instantNow: Instant, instantInThePast: Instant): String {
        val days = ChronoUnit.DAYS.between(instantInThePast, instantNow)
        if (days > 365) {
            val dateTime = LocalDateTime.ofInstant(instantInThePast, zoneId)
            return formatters[0].format(dateTime)
        }
        val hours = ChronoUnit.HOURS.between(instantInThePast, instantNow)
        if (hours > 23) {
            val dateTime = LocalDateTime.ofInstant(instantInThePast, zoneId)
            return formatters[1].format(dateTime)
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

}