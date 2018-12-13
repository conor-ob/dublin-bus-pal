package ie.dublinbuspal.repository.stopservice

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.dao.StopServiceDao
import ie.dublinbuspal.data.entity.StopServiceEntity
import ie.dublinbuspal.model.stopservice.StopService
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import io.reactivex.Observable

class StopServicePersister(private val dao: StopServiceDao,
                           private val entityMapper: Mapper<StopServiceResponseXml, StopServiceEntity>,
                           private val domainMapper: Mapper<StopServiceEntity, StopService>) : RoomPersister<StopServiceResponseXml, StopService, String> {

    override fun read(key: String): Observable<StopService> {
        return dao.select(key)
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, xml: StopServiceResponseXml) {
        val stopService = entityMapper.map(xml)
        stopService.id = key
        dao.insert(stopService)
    }

}
