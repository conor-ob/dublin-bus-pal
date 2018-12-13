package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.StopDao
import ie.dublinbuspal.data.entity.StopEntity
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import io.reactivex.Observable

class StopPersister(private val stopDao: StopDao,
                    private val txRunner: TxRunner,
                    private val entityMapper: Mapper<StopXml, StopEntity>,
                    private val domainMapper: Mapper<StopEntity, Stop>) : RoomPersister<StopsResponseXml, List<Stop>, String> {

    override fun read(key: String): Observable<List<Stop>> {
        return stopDao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, xml: StopsResponseXml) {
        txRunner.runInTx {
            stopDao.deleteAll()
            stopDao.insertAll(entityMapper.map(xml.stops))
        }
    }

}
