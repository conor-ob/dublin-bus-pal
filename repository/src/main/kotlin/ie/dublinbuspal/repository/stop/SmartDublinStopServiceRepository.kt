package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.domain.repository.Repository
import ie.dublinbuspal.data.entity.SmartDublinStopServiceEntity
import ie.dublinbuspal.domain.usecase.nearby.SmartDublinKey
import io.reactivex.Observable

class SmartDublinStopServiceRepository(private val store: StoreRoom<List<SmartDublinStopServiceEntity>, SmartDublinKey>) : Repository<List<SmartDublinStopServiceEntity>, SmartDublinKey> {

    override fun get(key: SmartDublinKey): Observable<List<SmartDublinStopServiceEntity>> {
        return store.get(key)
    }

    override fun fetch(key: SmartDublinKey): Observable<List<SmartDublinStopServiceEntity>> {
        return store.fetch(key)
    }

}
