package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class DefaultRouteServiceEntityTest : AbstractDataClassTest<DefaultRouteServiceEntity>() {

    override fun newInstance1(): DefaultRouteServiceEntity {
        return DefaultRouteServiceEntity("123", "here to there", "here", "there", listOf("1", "2"), listOf("3", "4"))
    }

    override fun newInstance2(): DefaultRouteServiceEntity {
        return DefaultRouteServiceEntity("777", "going somewhere", "not sure", "over that way", listOf("1"), listOf("3", "7"))
    }

}
