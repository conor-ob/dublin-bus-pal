package ie.dublinbuspal.data.entity

import ie.dublinbuspal.AbstractDataClassTest

class FavouriteStopEntityTest : AbstractDataClassTest<FavouriteStopEntity>() {

    override fun newInstance1(): FavouriteStopEntity {
        return FavouriteStopEntity("123", "My Bus Stop", listOf("46A", "39A"), 0)
    }

    override fun newInstance2(): FavouriteStopEntity {
        return FavouriteStopEntity("777", "My Favourite Bus Stop", listOf("7A"), 2)
    }

}
