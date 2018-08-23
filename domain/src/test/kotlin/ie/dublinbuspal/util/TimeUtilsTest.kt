package ie.dublinbuspal.util

import org.junit.Test

class TimeUtilsTest {

    @Test
    fun test() {
        val timestamp = "Wed, 06 Jun 2018 15:18:47 GMT"
        val time = TimeUtils.parse(timestamp)
        System.out.print(time)
    }

}
