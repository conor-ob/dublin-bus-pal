package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DefaultRouteDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.entity.PersisterEntity
import ie.dublinbuspal.mapping.route.DefaultRouteDomainMapper
import ie.dublinbuspal.mapping.route.DefaultRouteEntityMapper
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import io.reactivex.Observable

class DefaultRoutePersister(
        memoryPolicy: MemoryPolicy,
        private val persisterDao: PersisterDao,
        private val dao: DefaultRouteDao,
        private val txRunner: TxRunner,
        private val entityMapper: DefaultRouteEntityMapper,
        private val domainMapper: DefaultRouteDomainMapper
) : AbstractPersister<RoutesResponseXml, List<DefaultRoute>, String>(memoryPolicy, persisterDao) {

    override fun read(key: String): Observable<List<DefaultRoute>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, xml: RoutesResponseXml) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(xml.routes))
            persisterDao.insert(PersisterEntity(key))
        }
    }

}
