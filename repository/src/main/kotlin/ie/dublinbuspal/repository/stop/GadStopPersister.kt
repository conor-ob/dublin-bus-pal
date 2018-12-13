package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.GadStopDao
import ie.dublinbuspal.data.entity.GadStopEntity
import ie.dublinbuspal.model.stop.SmartDublinStop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Observable

class GadStopPersister(
        private val dao: GadStopDao,
        private val txRunner: TxRunner,
        private val entityMapper: Mapper<StopJson, GadStopEntity>,
        private val domainMapper: Mapper<GadStopEntity, SmartDublinStop>
) : RoomPersister<StopsResponseJson, List<SmartDublinStop>, String> {

    override fun read(key: String): Observable<List<SmartDublinStop>> {
        return dao.selectAll()
                .map { domainMapper.map(it) }
                .toObservable()
    }

    override fun write(key: String, json: StopsResponseJson) {
        txRunner.runInTx {
            dao.deleteAll()
            dao.insertAll(entityMapper.map(json.stops!!))
        }
    }

}
