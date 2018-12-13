package ie.dublinbuspal.repository.routeservice

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.dao.RouteServiceDao
import ie.dublinbuspal.data.entity.RouteServiceEntity
import ie.dublinbuspal.model.routeservice.RouteService
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import io.reactivex.Observable

class RouteServicePersister(private val dao: RouteServiceDao,
                            private val entityMapper: Mapper<RouteServiceResponseXml, RouteServiceEntity>,
                            private val domainMapper: Mapper<RouteServiceEntity, RouteService>) : RoomPersister<RouteServiceResponseXml, RouteService, String> {

    override fun read(key: String): Observable<RouteService> {
        return dao.select(key)
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, xml: RouteServiceResponseXml) {
        val routeService = entityMapper.map(xml)
        routeService.id = key
        dao.insert(routeService)
    }

}
