package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DublinBusStopDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.entity.DublinBusStopEntity
import ie.dublinbuspal.data.entity.PersisterEntity
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Observable

class DublinBusStopPersister(
        memoryPolicy: MemoryPolicy,
        private val persisterDao: PersisterDao,
        private val dao: DublinBusStopDao,
        private val txRunner: TxRunner,
        private val entityMapper: Mapper<StopJson, DublinBusStopEntity>,
        private val domainMapper: Mapper<DublinBusStopEntity, DublinBusStop>
) : AbstractPersister<StopsResponseJson, List<DublinBusStop>, String>(memoryPolicy, persisterDao) {

    override fun read(key: String): Observable<List<DublinBusStop>> {
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
