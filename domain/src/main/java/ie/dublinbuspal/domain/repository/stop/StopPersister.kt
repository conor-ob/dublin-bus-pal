package ie.dublinbuspal.domain.repository.stop

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.dao.StopDao
import ie.dublinbuspal.database.entity.StopEntity
import ie.dublinbuspal.domain.model.stop.Stop
import ie.dublinbuspal.service.model.stop.StopXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import io.reactivex.Observable

class StopPersister(private val dao: StopDao,
                    private val txRunner: TxRunner,
                    private val entityMapper: Mapper<StopXml, StopEntity>,
                    private val domainMapper: Mapper<StopEntity, Stop>) : RoomPersister<StopsResponseXml, List<Stop>, StopsRequestXml> {

    override fun read(key: StopsRequestXml): Observable<List<Stop>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: StopsRequestXml, xml: StopsResponseXml) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(xml.stops))
        }
    }

}