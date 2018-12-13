package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.model.livedata.Destination
import ie.dublinbuspal.model.livedata.DueTime
import ie.dublinbuspal.model.livedata.RealTimeStopData
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.livedata.RealTimeStopDataDataXml
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

class DefaultLiveDataEntityMapper : Mapper<RealTimeStopDataDataXml, RealTimeStopData> {

    override fun map(from: RealTimeStopDataDataXml): RealTimeStopData {
        return RealTimeStopData(from.routeId!!, mapDestination(from.destination!!), mapDueTime(from.timestamp!!, from.expectedTimestamp!!))
    }

    private fun mapDestination(destination: String): Destination {
        return Destination(getDestination(destination), getVia(destination))
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

    //TODO duplicate code
    private fun getDestination(destination: String): String {
        if (destination.contains("via")) {
            val i = destination.indexOf("via")
            return destination.substring(0, i).trim { it <= ' ' }
        }
        return destination
    }

    private fun getVia(destination: String): String? {
        if (destination.contains("via")) {
            val i = destination.indexOf("via")
            return destination.substring(i, destination.length).trim { it <= ' ' }
        }
        return null
    }

}
