package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class StopEntityTest : AbstractDataClassTest<StopEntity>() {

    override fun newInstance1(): StopEntity {
        return StopEntity("123", "Stop 1", 6.8416, -8.1251)
    }

    override fun newInstance2(): StopEntity {
        return StopEntity("234", "Stop 2", 84.12, 25.68512)
    }

}
