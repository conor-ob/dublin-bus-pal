package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class DetailedStopEntityTest : AbstractDataClassTest<DetailedStopEntity>() {

    override fun newInstance1(): DetailedStopEntity {
        return DetailedStopEntity("123", "Bus Stop 1", 65.151, 82.216, routes = listOf("1A", "2B"))
    }

    override fun newInstance2(): DetailedStopEntity {
        return DetailedStopEntity("456", "Bus Stop 2", -9.6214, 1.111)
    }

}
