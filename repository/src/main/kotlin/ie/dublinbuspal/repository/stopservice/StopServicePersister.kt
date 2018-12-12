package ie.dublinbuspal.repository.stopservice

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.dao.StopServiceDao
import ie.dublinbuspal.data.entity.StopServiceEntity
import ie.dublinbuspal.model.stopservice.StopService
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import io.reactivex.Observable

class StopServicePersister(private val dao: StopServiceDao,
                           private val entityMapper: Mapper<StopServiceResponseXml, StopServiceEntity>,
                           private val domainMapper: Mapper<StopServiceEntity, StopService>) : RoomPersister<StopServiceResponseXml, StopService, StopServiceRequestXml> {

    override fun read(key: StopServiceRequestXml): Observable<StopService> {
        return dao.select(key.stopServiceRequestBodyXml.stopServiceRequestRootXml.stopId)
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: StopServiceRequestXml, xml: StopServiceResponseXml) {
        val stopService = entityMapper.map(xml)
        stopService.id = key.stopServiceRequestBodyXml.stopServiceRequestRootXml.stopId
        dao.insert(stopService)
    }

}
