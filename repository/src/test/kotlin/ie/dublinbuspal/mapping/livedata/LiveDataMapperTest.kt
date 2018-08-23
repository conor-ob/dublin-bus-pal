package ie.dublinbuspal.mapping.livedata

import ie.dublinbuspal.service.model.livedata.LiveDataXml
import org.junit.Assert
import org.junit.Test

class LiveDataMapperTest {

    @Test
    fun testMap() {
        val xml = buildXml()
        val mapper = LiveDataMapper()
        val liveData = mapper.map(xml)
        Assert.assertEquals(13L, liveData.dueTime.minutes)
        Assert.assertEquals("22:59", liveData.dueTime.time)
    }

    private fun buildXml(): LiveDataXml {
        return LiveDataXml("145", "Heuston Station", "2018-07-11T22:46:51+01:00", "2018-07-11T22:59:56+01:00")
    }

}
