package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class RouteServiceEntityTest : AbstractDataClassTest<RouteServiceEntity>() {

    override fun newInstance1(): RouteServiceEntity {
        return RouteServiceEntity("123", "here to there", "here", "there", listOf("1", "2"), listOf("3", "4"))
    }

    override fun newInstance2(): RouteServiceEntity {
        return RouteServiceEntity("777", "going somewhere", "not sure", "over that way", listOf("1"), listOf("3", "7"))
    }

}
