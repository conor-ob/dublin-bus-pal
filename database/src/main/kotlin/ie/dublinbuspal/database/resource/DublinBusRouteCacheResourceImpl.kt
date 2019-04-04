package ie.dublinbuspal.database.resource

import ie.dublinbuspal.data.TxRunner
import ie.dublinbuspal.data.dao.DublinBusRouteDao
import ie.dublinbuspal.data.dao.DublinBusRouteInfoDao
import ie.dublinbuspal.data.dao.DublinBusRouteVariantDao
import ie.dublinbuspal.data.entity.DublinBusRouteEntity
import ie.dublinbuspal.data.entity.DublinBusRouteInfoEntity
import ie.dublinbuspal.data.entity.DublinBusRouteVariantEntity
import ie.dublinbuspal.data.resource.DublinBusRouteCacheResource
import io.reactivex.Maybe

class DublinBusRouteCacheResourceImpl(
        private val dublinBusRouteInfoDao: DublinBusRouteInfoDao,
        private val dublinBusRouteVariantDao: DublinBusRouteVariantDao,
        private val dublinBusRouteDao: DublinBusRouteDao,
        private val txRunner: TxRunner
) : DublinBusRouteCacheResource {

    override fun selectRoutes(): Maybe<List<DublinBusRouteEntity>> {
        return dublinBusRouteDao.selectAll()
    }

    override fun insertRoutes(stops: Pair<List<DublinBusRouteInfoEntity>, List<DublinBusRouteVariantEntity>>) {
        txRunner.runInTx {
            dublinBusRouteInfoDao.deleteAll()
            dublinBusRouteInfoDao.insertAll(stops.first)
            dublinBusRouteVariantDao.insertAll(stops.second)
        }
    }

}
