package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DefaultRouteDao
import ie.dublinbuspal.mapping.route.DefaultRouteDomainMapper
import ie.dublinbuspal.mapping.route.DefaultRouteEntityMapper
import ie.dublinbuspal.model.route.DefaultRoute
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import io.reactivex.Observable

class DefaultRoutePersister(private val dao: DefaultRouteDao,
                            private val txRunner: TxRunner,
                            private val entityMapper: DefaultRouteEntityMapper,
                            private val domainMapper: DefaultRouteDomainMapper) : RoomPersister<RoutesResponseXml, List<DefaultRoute>, String> {

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
