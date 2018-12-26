package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.GoAheadDublinRouteDao
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.entity.GoAheadDublinRouteEntity
import ie.dublinbuspal.data.entity.PersisterEntity
import ie.dublinbuspal.model.route.GoAheadDublinRoute
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsJson
import ie.dublinbuspal.service.model.route.RouteListInformationWithVariantsResponseJson
import io.reactivex.Observable

class GoAheadDublinRoutePersister(
        memoryPolicy: MemoryPolicy,
        private val persisterDao: PersisterDao,
        private val dao: GoAheadDublinRouteDao,
        private val txRunner: TxRunner,
        private val entityMapper: Mapper<RouteListInformationWithVariantsJson, GoAheadDublinRouteEntity>,
        private val domainMapper: Mapper<GoAheadDublinRouteEntity, GoAheadDublinRoute>
) : AbstractPersister<RouteListInformationWithVariantsResponseJson, List<GoAheadDublinRoute>, String>(memoryPolicy, persisterDao) {

    override fun read(key: String): Observable<List<GoAheadDublinRoute>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, json: RouteListInformationWithVariantsResponseJson) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(json.routes))
            persisterDao.insert(PersisterEntity(key))
        }
    }

}
