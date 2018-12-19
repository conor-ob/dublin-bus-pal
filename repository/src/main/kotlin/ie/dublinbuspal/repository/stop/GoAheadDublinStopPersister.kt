package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.GoAheadDublinStopDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.entity.GoAheadDublinStopEntity
import ie.dublinbuspal.data.entity.PersisterEntity
import ie.dublinbuspal.model.stop.DublinBusGoAheadDublinStop
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Observable

class GoAheadDublinStopPersister(
        memoryPolicy: MemoryPolicy,
        private val persisterDao: PersisterDao,
        private val dao: GoAheadDublinStopDao,
        private val txRunner: TxRunner,
        private val entityMapper: Mapper<StopJson, GoAheadDublinStopEntity>,
        private val domainMapper: Mapper<GoAheadDublinStopEntity, DublinBusGoAheadDublinStop>
) : AbstractPersister<StopsResponseJson, List<DublinBusGoAheadDublinStop>, String>(memoryPolicy, persisterDao) {

    override fun read(key: String): Observable<List<DublinBusGoAheadDublinStop>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, json: StopsResponseJson) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(json.stops))
            persisterDao.insert(PersisterEntity(key))
        }
    }

}
