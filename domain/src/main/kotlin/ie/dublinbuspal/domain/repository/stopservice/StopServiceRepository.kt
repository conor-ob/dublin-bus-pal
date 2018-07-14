package ie.dublinbuspal.domain.repository.stopservice

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.stopservice.StopService
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import io.reactivex.Observable

class StopServiceRepository(private val store: StoreRoom<StopService, StopServiceRequestXml>) : Repository<StopService, StopServiceRequestXml> {

    override fun get(key: StopServiceRequestXml): Observable<StopService> {
        return store.get(key)
    }

    override fun fetch(key: StopServiceRequestXml): Observable<StopService> {
        return store.fetch(key)
    }

}
