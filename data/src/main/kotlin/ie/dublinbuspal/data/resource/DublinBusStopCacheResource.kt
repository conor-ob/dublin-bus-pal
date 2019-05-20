package ie.dublinbuspal.data.resource

import ie.dublinbuspal.data.entity.DublinBusStopEntity
import ie.dublinbuspal.data.entity.DublinBusStopLocationEntity
import ie.dublinbuspal.data.entity.DublinBusStopServiceEntity
import io.reactivex.Maybe

interface DublinBusStopCacheResource {

    fun selectStops(): Maybe<List<DublinBusStopEntity>>

    fun insertStops(stops: Pair<List<DublinBusStopLocationEntity>, List<DublinBusStopServiceEntity>>)

}
