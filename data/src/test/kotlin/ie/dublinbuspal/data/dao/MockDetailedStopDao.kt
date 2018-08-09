package ie.dublinbuspal.data.dao

import ie.dublinbuspal.data.dao.DetailedStopDao
import ie.dublinbuspal.data.entity.DetailedStopEntity
import io.reactivex.Maybe

class MockDetailedStopDao : DetailedStopDao {

    private val entities = mutableMapOf<String, DetailedStopEntity>()

    override fun selectAll(): Maybe<List<DetailedStopEntity>> {
        return Maybe.just(entities.values.toList())
    }

    override fun selectAllFavourites(): Maybe<List<DetailedStopEntity>> {
        return Maybe.just(entities.values.toList().filter { it.favouriteName != null })
    }

}
