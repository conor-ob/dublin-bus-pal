package ie.dublinbuspal.repository.routeservice

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DefaultRouteServiceDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.entity.DefaultRouteServiceEntity
import ie.dublinbuspal.data.entity.PersisterEntity
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import io.reactivex.Observable

class DefaultRouteServicePersister(
        memoryPolicy: MemoryPolicy,
        private val persisterDao: PersisterDao,
        private val dao: DefaultRouteServiceDao,
        private val txRunner: TxRunner,
        private val entityMapper: Mapper<RouteServiceResponseXml, DefaultRouteServiceEntity>,
        private val domainMapper: Mapper<DefaultRouteServiceEntity, RouteService>
) : AbstractPersister<RouteServiceResponseXml, RouteService, String>(memoryPolicy, persisterDao) {

    override fun read(key: String): Observable<RouteService> {
        return dao.select(key)
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, xml: RouteServiceResponseXml) {
        val routeService = entityMapper.map(xml)
        routeService.id = key
        txRunner.runInTx {
            dao.insert(routeService)
            persisterDao.insert(PersisterEntity(key))
        }
    }

}
