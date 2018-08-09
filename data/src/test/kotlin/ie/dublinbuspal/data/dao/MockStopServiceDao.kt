package ie.dublinbuspal.data.dao

import ie.dublinbuspal.data.dao.StopServiceDao
import ie.dublinbuspal.data.entity.StopServiceEntity
import io.reactivex.Maybe

class MockStopServiceDao : StopServiceDao {

    private val entities = mutableMapOf<String, StopServiceEntity>()

    override fun select(id: String): Maybe<StopServiceEntity> {
        return Maybe.just(entities[id])
    }

    override fun insert(entity: StopServiceEntity) {
        entities[entity.id] = entity
    }

    override fun insertAll(entities: List<StopServiceEntity>) {
        for (entity in entities) {
            this.entities[entity.id] = entity
        }
    }

    override fun deleteAll() {
        entities.clear()
    }

}
