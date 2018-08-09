package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.domain.model.stopservice.SmartDublinStopService
import ie.dublinbuspal.domain.repository.Repository
import io.reactivex.Observable

class SmartDublinStopServiceRepository(private val store: StoreRoom<List<SmartDublinStopService>, SmartDublinKey>) : Repository<List<SmartDublinStopService>, Any> {

    override fun get(key: Any): Observable<List<SmartDublinStopService>> {
        return store.get(this.key)
    }

    override fun fetch(key: Any): Observable<List<SmartDublinStopService>> {
        return store.fetch(this.key)
    }

    val key : SmartDublinKey by lazy {
        return@lazy SmartDublinKey("bac", "json")
    }

}
