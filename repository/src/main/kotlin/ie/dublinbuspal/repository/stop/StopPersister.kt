package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DetailedStopDao
import ie.dublinbuspal.data.dao.StopDao
import ie.dublinbuspal.data.entity.DetailedStopEntity
import ie.dublinbuspal.data.entity.StopEntity
import ie.dublinbuspal.model.stop.Stop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import io.reactivex.Observable

class StopPersister(private val stopDao: StopDao,
                    private val detailedStopDao: DetailedStopDao,
                    private val txRunner: TxRunner,
                    private val entityMapper: Mapper<StopXml, StopEntity>,
                    private val domainMapper: Mapper<DetailedStopEntity, Stop>) : RoomPersister<StopsResponseXml, List<Stop>, StopsRequestXml> {

    override fun read(key: StopsRequestXml): Observable<List<Stop>> {
        return detailedStopDao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: StopsRequestXml, xml: StopsResponseXml) {
        txRunner.runInTx {
            stopDao.deleteAll()
            stopDao.insertAll(entityMapper.map(xml.stops))
        }
    }

}
