package ie.dublinbuspal.repository.stopservice

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DefaultStopServiceDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.entity.DefaultStopServiceEntity
import ie.dublinbuspal.data.entity.PersisterEntity
import ie.dublinbuspal.model.stopservice.DefaultStopService
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import io.reactivex.Observable

class DefaultStopServicePersister(
        memoryPolicy: MemoryPolicy,
        private val persisterDao: PersisterDao,
        private val dao: DefaultStopServiceDao,
        private val txRunner: TxRunner,
        private val entityMapper: Mapper<StopServiceResponseXml, DefaultStopServiceEntity>,
        private val domainMapper: Mapper<DefaultStopServiceEntity, DefaultStopService>
) : AbstractPersister<StopServiceResponseXml, DefaultStopService, String>(memoryPolicy, persisterDao) {

    override fun read(key: String): Observable<DefaultStopService> {
        return dao.select(key)
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, xml: StopServiceResponseXml) {
        val stopService = entityMapper.map(xml)
        stopService.id = key
        txRunner.runInTx {
            dao.insert(stopService)
            persisterDao.insert(PersisterEntity(key))
        }
    }

}
