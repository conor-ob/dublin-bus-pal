package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class StopServiceEntityTest : AbstractDataClassTest<StopServiceEntity>() {

    override fun newInstance1(): StopServiceEntity {
        return StopServiceEntity("415", listOf("46A", "747"))
    }

    override fun newInstance2(): StopServiceEntity {
        return StopServiceEntity("985", listOf("184"))
    }

}
