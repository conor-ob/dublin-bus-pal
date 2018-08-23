package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.LiveData
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.livedata.LiveDataXml
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

class LiveDataMapper : Mapper<LiveDataXml, LiveData> {

    override fun map(from: LiveDataXml): LiveData {
        return LiveData(from.routeId!!, from.destination!!, mapDueTime(from.timestamp!!, from.expectedTimestamp!!))
    }

    private fun mapDueTime(timestamp: String, expectedTime: String): DueTime {
        val timestampInstant = OffsetDateTime
                .parse(timestamp, DateTimeFormatter.ISO_DATE_TIME)
                .toInstant()
        val expectedTimeInstant = OffsetDateTime
                .parse(expectedTime, DateTimeFormatter.ISO_DATE_TIME)
                .toInstant()
        val zonedTimestamp = ZonedDateTime.ofInstant(timestampInstant, ZoneId.of(TimeZone.getDefault().id))
        val zonedExpectedTimestamp = ZonedDateTime.ofInstant(expectedTimeInstant, ZoneId.of(TimeZone.getDefault().id))

        val minutes = ChronoUnit.MINUTES.between(timestampInstant, expectedTimeInstant)
        return DueTime(minutes, zonedExpectedTimestamp.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString())
    }

}
