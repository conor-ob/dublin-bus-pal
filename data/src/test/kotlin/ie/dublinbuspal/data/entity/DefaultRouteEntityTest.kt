package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class DefaultRouteEntityTest : AbstractDataClassTest<DefaultRouteEntity>() {

    override fun newInstance1(): DefaultRouteEntity {
        return DefaultRouteEntity("145", "here", "there")
    }

    override fun newInstance2(): DefaultRouteEntity {
        return DefaultRouteEntity("9", "The Shire", "Mordor")
    }

}
