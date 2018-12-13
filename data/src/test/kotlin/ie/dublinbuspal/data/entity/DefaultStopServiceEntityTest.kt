package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class DefaultStopServiceEntityTest : AbstractDataClassTest<DefaultStopServiceEntity>() {

    override fun newInstance1(): DefaultStopServiceEntity {
        return DefaultStopServiceEntity("415", listOf("46A", "747"))
    }

    override fun newInstance2(): DefaultStopServiceEntity {
        return DefaultStopServiceEntity("985", listOf("184"))
    }

}
