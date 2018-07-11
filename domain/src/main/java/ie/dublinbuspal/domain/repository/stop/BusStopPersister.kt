package ie.dublinbuspal.domain.repository.stop

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.dao.BusStopDao
import ie.dublinbuspal.database.entity.BusStopEntity
import ie.dublinbuspal.domain.model.stop.BusStop
import ie.dublinbuspal.service.model.stop.BusStopXml
import ie.dublinbuspal.service.model.stop.BusStopsRequestXml
import ie.dublinbuspal.service.model.stop.BusStopsResponseXml
import io.reactivex.Observable

class BusStopPersister(private val dao: BusStopDao,
                       private val txRunner: TxRunner,
                       private val entityMapper: Mapper<BusStopXml, BusStopEntity>,
                       private val domainMapper: Mapper<BusStopEntity, BusStop>)
    : RoomPersister<BusStopsResponseXml, List<BusStop>, BusStopsRequestXml> {

    override fun read(key: BusStopsRequestXml): Observable<List<BusStop>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: BusStopsRequestXml, xml: BusStopsResponseXml) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(xml.busStops!!))
        }
    }

}
