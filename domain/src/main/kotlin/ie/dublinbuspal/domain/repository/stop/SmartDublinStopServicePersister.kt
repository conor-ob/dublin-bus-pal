package ie.dublinbuspal.domain.repository.stop

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.base.Mapper
import ie.dublinbuspal.base.TxRunner
import ie.dublinbuspal.database.dao.SmartDublinStopServiceDao
import ie.dublinbuspal.database.entity.SmartDublinStopServiceEntity
import ie.dublinbuspal.service.model.stop.StopJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Observable

class SmartDublinStopServicePersister(private val dao: SmartDublinStopServiceDao,
                                      private val txRunner: TxRunner,
                                      private val entityMapper: Mapper<StopJson, SmartDublinStopServiceEntity>)
    : RoomPersister<StopsResponseJson, List<SmartDublinStopServiceEntity>, SmartDublinKey> {

    override fun read(key: SmartDublinKey): Observable<List<SmartDublinStopServiceEntity>> {
        return dao.selectAll().toObservable()
    }

    override fun write(key: SmartDublinKey, json: StopsResponseJson) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(json.stops!!))
        }
    }

}