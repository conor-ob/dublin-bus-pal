package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class DefaultStopEntityTest : AbstractDataClassTest<DefaultStopEntity>() {

    override fun newInstance1(): DefaultStopEntity {
        return DefaultStopEntity("123", "Stop 1", 6.8416, -8.1251)
    }

    override fun newInstance2(): DefaultStopEntity {
        return DefaultStopEntity("234", "Stop 2", 84.12, 25.68512)
    }

}
