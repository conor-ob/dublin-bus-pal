package ie.dublinbuspal.repository.stopservice

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.dao.DefaultStopServiceDao
import ie.dublinbuspal.data.entity.DefaultStopServiceEntity
import ie.dublinbuspal.model.stopservice.DefaultStopService
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import io.reactivex.Observable

class DefaultStopServicePersister(private val dao: DefaultStopServiceDao,
                                  private val entityMapper: Mapper<StopServiceResponseXml, DefaultStopServiceEntity>,
                                  private val domainMapper: Mapper<DefaultStopServiceEntity, DefaultStopService>) : RoomPersister<StopServiceResponseXml, DefaultStopService, String> {

    override fun read(key: String): Observable<DefaultStopService> {
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
