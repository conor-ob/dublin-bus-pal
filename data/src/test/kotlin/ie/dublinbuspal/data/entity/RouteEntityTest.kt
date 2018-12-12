package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class RouteEntityTest : AbstractDataClassTest<RouteEntity>() {

    override fun newInstance1(): RouteEntity {
        return RouteEntity("145", "here", "there")
    }

    override fun newInstance2(): RouteEntity {
        return RouteEntity("9", "The Shire", "Mordor")
    }

}
