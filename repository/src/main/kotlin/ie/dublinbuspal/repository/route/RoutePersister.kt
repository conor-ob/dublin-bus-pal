package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DefaultRouteDao
import ie.dublinbuspal.mapping.route.RouteDomainMapper
import ie.dublinbuspal.mapping.route.RouteEntityMapper
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import io.reactivex.Observable

class RoutePersister(private val dao: DefaultRouteDao,
                     private val txRunner: TxRunner,
                     private val entityMapper: RouteEntityMapper,
                     private val domainMapper: RouteDomainMapper) : RoomPersister<RoutesResponseXml, List<DefaultRoute>, String> {

    override fun read(key: String): Observable<List<DefaultRoute>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, xml: RoutesResponseXml) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(xml.routes))
        }
    }

}
