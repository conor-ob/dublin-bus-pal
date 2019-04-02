package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.DublinBusSoapApi
import ie.dublinbuspal.service.model.livedata.*
import ie.dublinbuspal.service.model.route.*
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestBodyXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestRootXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.stop.StopsRequestBodyXml
import ie.dublinbuspal.service.model.stop.StopsRequestRootXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.util.StringUtils
import io.reactivex.Single

class DublinBusSoapResourceAdapter(
        private val api: DublinBusSoapApi
) : DublinBusSoapResource {

    override fun getDublinBusStops(): Single<StopsResponseXml> {
        return api.getStops(dublinBusStopsKey)
                .map { adaptDublinBusStopsResponse(it) }
    }

    override fun getDublinBusRoutes(): Single<RoutesResponseXml> {
        return api.getRoutes(dublinBusRoutesKey)
                .map { adaptDublinBusRoutesResponse(it) }
    }

    override fun getDublinBusLiveData(stopId: String): Single<LiveDataResponseXml> {
        return api.getLiveData(buildDublinBusLiveDataKey(stopId))
                .map { adaptDublinBusLiveDataResponse(it) }
    }

    override fun getDublinBusRouteService(routeId: String): Single<RouteServiceResponseXml> {
        return api.getRouteService(buildDublinBusRouteServiceKey(routeId))
                .map { adaptDublinBusRouteServiceResponse(it) }
    }

    private fun adaptDublinBusStopsResponse(response: StopsResponseXml): StopsResponseXml {
        val filtered = response.stops
                .filter { it.id != null && it.name != null && it.latitude != null && it.longitude != null }
                .map { it.copy(id = it.id!!.trim(), name = it.name!!.trim(), latitude = it.latitude!!.trim(), longitude = it.longitude!!.trim()) }
        return response.copy(stops = filtered)
    }

    private fun adaptDublinBusRoutesResponse(response: RoutesResponseXml): RoutesResponseXml {
        val filtered = response.routes
                .filter { it.id != null && it.origin != null && it.destination != null && it.id != "1C" }
                .map { RouteXml(it.id!!.trim(), it.origin!!.trim(), it.destination!!.trim()) }
        return response.copy(routes = filtered)
    }

    private fun adaptDublinBusLiveDataResponse(response: LiveDataResponseXml): LiveDataResponseXml {
        val filtered = response.realTimeStopData
                .filter { it.routeId != null && it.destination != null && it.expectedTimestamp != null && it.timestamp != null }
                .map { RealTimeStopDataDataXml(it.routeId!!.trim(), it.destination!!.trim(), it.timestamp!!.trim(), it.expectedTimestamp!!.trim()) }
        return response.copy(realTimeStopData = filtered)
    }

    private fun adaptDublinBusRouteServiceResponse(response: RouteServiceResponseXml): RouteServiceResponseXml {
        return response //TODO
    }

    private val dublinBusStopsKey: StopsRequestXml by lazy {
        val root = StopsRequestRootXml()
        val body = StopsRequestBodyXml(root)
        return@lazy StopsRequestXml(body)
    }

    private val dublinBusRoutesKey: RoutesRequestXml by lazy {
        val root = RoutesRequestRootXml(StringUtils.EMPTY_STRING)
        val body = RoutesRequestBodyXml(root)
        return@lazy RoutesRequestXml(body)
    }

    private fun buildDublinBusLiveDataKey(stopId: String): LiveDataRequestXml {
        val root = LiveDataRequestRootXml(stopId, true.toString().toLowerCase())
        val body = LiveDataRequestBodyXml(root)
        return LiveDataRequestXml(body)
    }

    private fun buildDublinBusRouteServiceKey(routeId: String): RouteServiceRequestXml {
        val root = RouteServiceRequestRootXml(routeId)
        val body = RouteServiceRequestBodyXml(root)
        return RouteServiceRequestXml(body)
    }

}
