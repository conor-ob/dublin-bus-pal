package ie.dublinbuspal.domain.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.database.entity.SmartDublinStopServiceEntity
import io.reactivex.Observable

class SmartDublinStopServiceRepository(private val store: StoreRoom<List<SmartDublinStopServiceEntity>, SmartDublinKey>) : Repository<List<SmartDublinStopServiceEntity>, SmartDublinKey> {

    override fun get(key: SmartDublinKey): Observable<List<SmartDublinStopServiceEntity>> {
        return store.get(key)
    }

    override fun fetch(key: SmartDublinKey): Observable<List<SmartDublinStopServiceEntity>> {
        return store.fetch(key)
    }

}
