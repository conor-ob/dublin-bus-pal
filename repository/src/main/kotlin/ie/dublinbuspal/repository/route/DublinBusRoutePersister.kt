package ie.dublinbuspal.repository.route

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.resource.DublinBusRouteCacheResource
import ie.dublinbuspal.model.route.Route
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.service.api.RtpiRoute
import ie.dublinbuspal.service.api.rtpi.RtpiRouteListInformationWithVariantsJson
import ie.dublinbuspal.util.InternetManager
import io.reactivex.Maybe

class DublinBusRoutePersister(
        private val cacheResource: DublinBusRouteCacheResource,
        memoryPolicy: MemoryPolicy,
        persisterDao: PersisterDao,
        internetManager: InternetManager
) : AbstractPersister<List<RtpiRoute>, List<Route>, String>(memoryPolicy, persisterDao, internetManager) {

    override fun select(key: String): Maybe<List<Route>> {
        return cacheResource.selectRoutes().map { DublinBusRouteMapper.mapEntitiesToStops(it) }
    }

    override fun insert(key: String, raw: List<RtpiRoute>) {
        cacheResource.insertRoutes(DublinBusRouteMapper.mapJsonToEntities(raw))
    }

}
