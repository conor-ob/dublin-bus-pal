package ie.dublinbuspal.domain.repository.stop

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import io.reactivex.Observable

class StopRepository(private val store: StoreRoom<List<Stop>, StopsRequestXml>) : Repository<List<Stop>, StopsRequestXml> {

    override fun get(key: StopsRequestXml): Observable<List<Stop>> {
        return store.get(key)
    }

    override fun fetch(key: StopsRequestXml): Observable<List<Stop>> {
        return store.fetch(key)
    }

}
