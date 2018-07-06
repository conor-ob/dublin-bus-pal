package ie.dublinbuspal.domain.repository

import com.nytimes.android.external.store3.base.impl.room.StoreRoom
import ie.dublinbuspal.base.Repository
import ie.dublinbuspal.domain.model.BusStop
import ie.dublinbuspal.service.model.busstops.BusStopsRequestXml
import io.reactivex.Observable

class BusStopRepository(private val store: StoreRoom<List<BusStop>, BusStopsRequestXml>) : Repository<List<BusStop>, BusStopsRequestXml> {

    override fun get(key: BusStopsRequestXml): Observable<List<BusStop>> {
        return store.get(key)
    }

}
