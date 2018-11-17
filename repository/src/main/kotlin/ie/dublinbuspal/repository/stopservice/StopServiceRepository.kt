package ie.dublinbuspal.repository.stopservice

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stopservice.StopService
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestBodyXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestRootXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import io.reactivex.Observable

class StopServiceRepository(private val store: StoreRoom<StopService, StopServiceRequestXml>) : Repository<StopService> {

    override fun getAll(): Observable<List<StopService>> {
        throw UnsupportedOperationException()
    }

    override fun getById(id: String): Observable<StopService> {
        return store.get(buildKey(id))
    }

    override fun getAllById(id: String): Observable<List<StopService>> {
        throw UnsupportedOperationException()
    }

    private fun buildKey(stopId: String): StopServiceRequestXml {
        val root = StopServiceRequestRootXml(stopId)
        val body = StopServiceRequestBodyXml(root)
        return StopServiceRequestXml(body)
    }

}
