package ie.dublinbuspal.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Month
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class TimeUtilsTest {

    @Test
    fun `timestamps from official Dublin Bus API should be parsed correctly`() {
        val rawDateTimestamp = "2018-11-20T18:44:26+00:00"
        val instant = TimeUtils.dateTimeStampToInstant(rawDateTimestamp, Formatter.isoDateTime)
        val dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDateTime()
        assertThat(dateTime.dayOfWeek).isEqualTo(DayOfWeek.TUESDAY)
        assertThat(dateTime.dayOfMonth).isEqualTo(20)
        assertThat(dateTime.month).isEqualTo(Month.NOVEMBER)
        assertThat(dateTime.year).isEqualTo(2018)
        assertThat(dateTime.hour).isEqualTo(18)
        assertThat(dateTime.minute).isEqualTo(44)
        assertThat(dateTime.second).isEqualTo(26)
        val timestamp = TimeUtils.formatAsTime(instant)
        assertThat(timestamp).isEqualTo("18:44")
    }

    @Test
    fun `timestamps from RTPI API should be parsed correctly`() {
        val rawDateTimestamp = "21/02/2019 20:48:36"
        val instant = TimeUtils.dateTimeStampToInstant(rawDateTimestamp, Formatter.dateTime)
        val dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDateTime()
        assertThat(dateTime.dayOfWeek).isEqualTo(DayOfWeek.THURSDAY)
        assertThat(dateTime.dayOfMonth).isEqualTo(21)
        assertThat(dateTime.month).isEqualTo(Month.FEBRUARY)
        assertThat(dateTime.year).isEqualTo(2019)
        assertThat(dateTime.hour).isEqualTo(20)
        assertThat(dateTime.minute).isEqualTo(48)
        assertThat(dateTime.second).isEqualTo(36)
        val timestamp = TimeUtils.formatAsTime(instant)
        assertThat(timestamp).isEqualTo("20:48")
    }

    @Test
    fun `timestamps from RSS Dublin Bus API should be parsed correctly`() {
        val rawDateTimestamp = "Wed, 02 Jan 2019 10:59:21 GMT"
        val instant = TimeUtils.dateTimeStampToInstant(rawDateTimestamp, Formatter.zonedDateTime)
        val dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDateTime()
        assertThat(dateTime.dayOfWeek).isEqualTo(DayOfWeek.WEDNESDAY)
        assertThat(dateTime.dayOfMonth).isEqualTo(2)
        assertThat(dateTime.month).isEqualTo(Month.JANUARY)
        assertThat(dateTime.year).isEqualTo(2019)
        assertThat(dateTime.hour).isEqualTo(10)
        assertThat(dateTime.minute).isEqualTo(59)
        assertThat(dateTime.second).isEqualTo(21)
        val timestamp = TimeUtils.formatAsTime(instant)
        assertThat(timestamp).isEqualTo("10:59")
    }

}
