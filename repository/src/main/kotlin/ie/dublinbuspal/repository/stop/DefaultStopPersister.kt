package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DefaultStopDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.entity.DefaultStopEntity
import ie.dublinbuspal.data.entity.PersisterEntity
import ie.dublinbuspal.model.stop.DefaultStop
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.util.InternetManager
import io.reactivex.Observable

class DefaultStopPersister(
        memoryPolicy: MemoryPolicy,
        internetManager: InternetManager,
        private val persisterDao: PersisterDao,
        private val defaultStopDao: DefaultStopDao,
        private val txRunner: TxRunner,
        private val entityMapper: Mapper<StopXml, DefaultStopEntity>,
        private val domainMapper: Mapper<DefaultStopEntity, DefaultStop>
) : AbstractPersister<StopsResponseXml, List<DefaultStop>, String>(memoryPolicy, persisterDao, internetManager) {

    override fun read(key: String): Observable<List<DefaultStop>> {
        return defaultStopDao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, xml: StopsResponseXml) {
        txRunner.runInTx {
            defaultStopDao.deleteAll()
            defaultStopDao.insertAll(entityMapper.map(xml.stops))
            persisterDao.insert(PersisterEntity(key))
        }
    }

}
