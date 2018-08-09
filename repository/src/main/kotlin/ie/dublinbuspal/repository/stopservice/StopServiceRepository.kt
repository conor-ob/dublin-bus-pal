package ie.dublinbuspal.repository.stopservice

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.domain.model.stopservice.StopService
import ie.dublinbuspal.domain.repository.Repository
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestBodyXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestRootXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import io.reactivex.Observable

class StopServiceRepository(private val store: StoreRoom<StopService, StopServiceRequestXml>) : Repository<StopService, String> {

    override fun get(key: String): Observable<StopService> {
        return store.get(buildKey(key))
    }

    override fun fetch(key: String): Observable<StopService> {
        return store.fetch(buildKey(key))
    }

    private fun buildKey(stopId: String): StopServiceRequestXml {
        val root = StopServiceRequestRootXml(stopId)
        val body = StopServiceRequestBodyXml(root)
        return StopServiceRequestXml(body)
    }

}
