package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.SmartDublinStopServiceDao
import ie.dublinbuspal.data.entity.SmartDublinStopServiceEntity
import ie.dublinbuspal.domain.model.stopservice.SmartDublinStopService
import ie.dublinbuspal.domain.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Observable

class SmartDublinStopServicePersister(private val dao: SmartDublinStopServiceDao,
                                      private val txRunner: TxRunner,
                                      private val entityMapper: Mapper<StopJson, SmartDublinStopServiceEntity>,
                                      private val domainMapper: Mapper<SmartDublinStopServiceEntity, SmartDublinStopService>)
    : RoomPersister<StopsResponseJson, List<SmartDublinStopService>, SmartDublinKey> {

    override fun read(key: SmartDublinKey): Observable<List<SmartDublinStopService>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: SmartDublinKey, json: StopsResponseJson) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(json.stops!!))
        }
    }

}
