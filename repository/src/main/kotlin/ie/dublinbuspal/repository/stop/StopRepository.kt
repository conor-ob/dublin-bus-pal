package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Repository
import ie.dublinbuspal.service.model.stop.StopsRequestBodyXml
import ie.dublinbuspal.service.model.stop.StopsRequestRootXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import io.reactivex.Observable

class StopRepository(private val store: StoreRoom<List<Stop>, StopsRequestXml>): Repository<Stop> {

    val key: StopsRequestXml by lazy {
        val root = StopsRequestRootXml()
        val body = StopsRequestBodyXml(root)
        return@lazy StopsRequestXml(body)
    }

    override fun getAll(): Observable<List<Stop>> {
        return store.get(this.key)
    }

    override fun getById(id: String): Observable<Stop> {
        throw UnsupportedOperationException()
    }

    override fun getAllById(id: String): Observable<List<Stop>> {
        throw UnsupportedOperationException()
    }

}