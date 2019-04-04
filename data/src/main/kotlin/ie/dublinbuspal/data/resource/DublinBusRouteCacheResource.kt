package ie.dublinbuspal.data.resource

import ie.dublinbuspal.data.entity.DublinBusRouteEntity
import ie.dublinbuspal.data.entity.DublinBusRouteInfoEntity
import ie.dublinbuspal.data.entity.DublinBusRouteVariantEntity
import io.reactivex.Maybe

interface DublinBusRouteCacheResource {

    fun selectRoutes(): Maybe<List<DublinBusRouteEntity>>

    fun insertRoutes(stops: Pair<List<DublinBusRouteInfoEntity>, List<DublinBusRouteVariantEntity>>)

}
