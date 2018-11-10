package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class SmartDublinStopServiceEntityTest : AbstractDataClassTest<SmartDublinStopServiceEntity>() {

    override fun newInstance1(): SmartDublinStopServiceEntity {
        return SmartDublinStopServiceEntity("191", listOf("46A", "145", "39"))
    }

    override fun newInstance2(): SmartDublinStopServiceEntity {
        return SmartDublinStopServiceEntity("415", listOf("7A", "4", "7"))
    }

}
