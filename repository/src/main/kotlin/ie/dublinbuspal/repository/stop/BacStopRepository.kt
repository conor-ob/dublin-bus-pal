package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stop.SmartDublinStop
import ie.dublinbuspal.repository.Repository

class BacStopRepository(
        private val store: StoreRoom<List<SmartDublinStop>, SmartDublinKey>
) : Repository<List<SmartDublinStop>, Any> {

    private val key : SmartDublinKey by lazy { SmartDublinKey("bac", "json") }

    override fun get(key: Any) = store.get(this.key)

    override fun fetch(key: Any) = store.fetch(this.key)

}
