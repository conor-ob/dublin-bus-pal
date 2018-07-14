package ie.dublinbuspal.database.dao

import ie.dublinbuspal.database.entity.StopEntity
import io.reactivex.Maybe

class MockStopDao : StopDao {

    private val entities = mutableMapOf<String, StopEntity>()

    override fun selectAll(): Maybe<List<StopEntity>> {
        return Maybe.just(entities.values.toList())
    }

    override fun insertAll(entities: List<StopEntity>) {
        for (entity in entities) {
            this.entities[entity.id] = entity
        }
    }

    override fun deleteAll() {
        entities.clear()
    }

}
