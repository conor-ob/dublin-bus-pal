package ie.dublinbuspal.database.dao

import ie.dublinbuspal.database.entity.DetailedStopEntity
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
