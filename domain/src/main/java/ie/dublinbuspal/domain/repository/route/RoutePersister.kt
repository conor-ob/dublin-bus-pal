package ie.dublinbuspal.domain.repository.route

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.dao.RouteDao
import ie.dublinbuspal.domain.mapping.route.RouteDomainMapper
import ie.dublinbuspal.domain.mapping.route.RouteEntityMapper
import ie.dublinbuspal.domain.model.route.Route
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import io.reactivex.Observable

class RoutePersister(private val dao: RouteDao,
                     private val txRunner: TxRunner,
                     private val entityMapper: RouteEntityMapper,
                     private val domainMapper: RouteDomainMapper) : RoomPersister<RoutesResponseXml, List<Route>, RoutesRequestXml> {

    override fun read(key: RoutesRequestXml): Observable<List<Route>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: RoutesRequestXml, xml: RoutesResponseXml) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(xml.routes))
        }
    }

}
