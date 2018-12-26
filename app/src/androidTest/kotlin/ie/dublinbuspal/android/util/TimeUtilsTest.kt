package ie.dublinbuspal.android.util

import androidx.test.core.app.ApplicationProvider
import com.jakewharton.threetenabp.AndroidThreeTen
import ie.dublinbuspal.util.TimeUtils
import org.junit.BeforeClass
import org.junit.Test
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.zone.ZoneRulesProvider
import java.lang.Exception

class TimeUtilsTest {

    companion object {

        @BeforeClass
        @JvmStatic
        fun setup() {
            AndroidThreeTen.init(ApplicationProvider.getApplicationContext())
            val ids = ZoneRulesProvider.getAvailableZoneIds()
        }

    }

    @Test
    fun testDaylightSavings() {
        val timestamp = "2018-12-16T20:51:33+00:00"
        val instant = TimeUtils.toInstant(timestamp)
    }

    @Test
    fun testSummerTime() {
        val timestamp = "2018-07-11T22:56:51+01:00"
        val instant = TimeUtils.toInstant(timestamp)
    }

    @Test
    fun testCustomFormat0() {
        val timestamp = "16/12/2018 17:37:58"
        val instant = TimeUtils.toInstant(timestamp)
    }

    @Test
    fun testCustomFormat1() {
        val timestamp = "Tue, 11 Dec 2018 08:50:10 GMT"

        try {
            val test1 = OffsetDateTime.parse(timestamp)
        } catch (e: Exception) {
            print(e)
        }

        try {
            val test2 = ZonedDateTime.parse(timestamp)
        } catch (e: Exception) {
            print(e)
        }

        try {
            val test3 = LocalDateTime.parse(timestamp)
        } catch (e: Exception) {
            print(e)
        }

        val format = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")

        val instant = TimeUtils.toInstant(timestamp)
    }

}
