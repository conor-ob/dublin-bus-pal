package ie.dublinbuspal.database.resource

import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DublinBusStopDao
import ie.dublinbuspal.data.dao.DublinBusStopLocationDao
import ie.dublinbuspal.data.dao.DublinBusStopServiceDao
import ie.dublinbuspal.data.entity.DublinBusStopEntity
import ie.dublinbuspal.data.entity.DublinBusStopLocationEntity
import ie.dublinbuspal.data.entity.DublinBusStopServiceEntity
import ie.dublinbuspal.data.resource.DublinBusStopCacheResource
import io.reactivex.Maybe

class DublinBusStopCacheResourceImpl(
        private val dublinBusStopLocationDao: DublinBusStopLocationDao,
        private val dublinBusStopServiceDao: DublinBusStopServiceDao,
        private val dublinBusStopDao: DublinBusStopDao,
        private val txRunner: TxRunner
) : DublinBusStopCacheResource {

    override fun selectStops(): Maybe<List<DublinBusStopEntity>> {
        return dublinBusStopDao.selectAll()
    }

    override fun insertStops(stops: Pair<List<DublinBusStopLocationEntity>, List<DublinBusStopServiceEntity>>) {
        txRunner.runInTx {
            dublinBusStopLocationDao.deleteAll()
            dublinBusStopLocationDao.insertAll(stops.first)
            dublinBusStopServiceDao.insertAll(stops.second)
        }
    }

}
