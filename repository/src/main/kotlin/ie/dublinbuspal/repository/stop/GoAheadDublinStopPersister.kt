package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.GoAheadDublinStopDao
import ie.dublinbuspal.data.entity.GoAheadDublinStopEntity
import ie.dublinbuspal.model.stop.DublinBusGoAheadDublinStop
import ie.dublinbuspal.repository.Mapper
import ie.dublinbuspal.service.model.stop.StopJson
import ie.dublinbuspal.service.model.stop.StopsResponseJson
import io.reactivex.Observable

class GoAheadDublinStopPersister(
        private val dao: GoAheadDublinStopDao,
        private val txRunner: TxRunner,
        private val entityMapper: Mapper<StopJson, GoAheadDublinStopEntity>,
        private val domainMapper: Mapper<GoAheadDublinStopEntity, DublinBusGoAheadDublinStop>
) : RoomPersister<StopsResponseJson, List<DublinBusGoAheadDublinStop>, String> {

    override fun read(key: String): Observable<List<DublinBusGoAheadDublinStop>> {
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
