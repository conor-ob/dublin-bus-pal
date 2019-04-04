package ie.dublinbuspal.repository.stop

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.resource.DublinBusStopCacheResource
import ie.dublinbuspal.mapping.stop.DublinBusStopMapper
import ie.dublinbuspal.model.stop.DublinBusStop
import ie.dublinbuspal.repository.AbstractPersister
import ie.dublinbuspal.util.InternetManager
import ie.rtpi.api.rtpi.RtpiBusStopInformationJson
import io.reactivex.Maybe

class DublinBusStopPersister(
        private val cacheResource: DublinBusStopCacheResource,
        memoryPolicy: MemoryPolicy,
        persisterDao: PersisterDao,
        internetManager: InternetManager
) : AbstractPersister<List<RtpiBusStopInformationJson>, List<DublinBusStop>, String>(memoryPolicy, persisterDao, internetManager) {

    override fun select(key: String): Maybe<List<DublinBusStop>> {
        return cacheResource.selectStops().map { DublinBusStopMapper.mapEntitiesToStops(it) }
    }

    override fun insert(key: String, raw: List<RtpiBusStopInformationJson>) {
        cacheResource.insertStops(DublinBusStopMapper.mapJsonToEntities(raw))
    }

}
