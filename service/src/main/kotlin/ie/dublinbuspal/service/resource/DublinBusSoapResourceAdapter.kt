package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.DublinBusSoapApi
import ie.dublinbuspal.service.model.livedata.LiveDataRequestBodyXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestRootXml
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.route.RoutesRequestBodyXml
import ie.dublinbuspal.service.model.route.RoutesRequestRootXml
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestBodyXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestRootXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.status.ServiceStatusRequestBodyXml
import ie.dublinbuspal.service.model.status.ServiceStatusRequestRootXml
import ie.dublinbuspal.service.model.status.ServiceStatusRequestXml
import ie.dublinbuspal.service.model.status.ServiceStatusResponseXml
import ie.dublinbuspal.service.model.stop.StopsRequestBodyXml
import ie.dublinbuspal.service.model.stop.StopsRequestRootXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestBodyXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestRootXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import ie.dublinbuspal.util.StringUtils
import io.reactivex.Single

class DublinBusSoapResourceAdapter(
        private val api: DublinBusSoapApi
) : DublinBusSoapResource {

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

    private val dublinBusServiceStatusKey: ServiceStatusRequestXml by lazy {
        val root = ServiceStatusRequestRootXml()
        val body = ServiceStatusRequestBodyXml(root)
        return@lazy ServiceStatusRequestXml(body)
    }

    override fun getDublinBusStops(): Single<StopsResponseXml> {
        return api.getStops(dublinBusStopsKey)
    }

    override fun getDublinBusRoutes(): Single<RoutesResponseXml> {
        return api.getRoutes(dublinBusRoutesKey)
    }

    override fun getDublinBusLiveData(stopId: String): Single<LiveDataResponseXml> {
        return api.getLiveData(buildDublinBusLiveDataKey(stopId))
    }

    private fun buildDublinBusLiveDataKey(stopId: String): LiveDataRequestXml {
        val root = LiveDataRequestRootXml(stopId, true.toString().toLowerCase())
        val body = LiveDataRequestBodyXml(root)
        return LiveDataRequestXml(body)
    }

    override fun getDublinBusStopService(stopId: String): Single<StopServiceResponseXml> {
        return api.getStopService(buildDublinBusStopServiceKey(stopId))
    }

    private fun buildDublinBusStopServiceKey(stopId: String): StopServiceRequestXml {
        val root = StopServiceRequestRootXml(stopId)
        val body = StopServiceRequestBodyXml(root)
        return StopServiceRequestXml(body)
    }

    override fun getDublinBusRouteService(routeId: String): Single<RouteServiceResponseXml> {
        return api.getRouteService(buildDublinBusRouteServiceKey(routeId))
    }

    private fun buildDublinBusRouteServiceKey(routeId: String): RouteServiceRequestXml {
        val root = RouteServiceRequestRootXml(routeId)
        val body = RouteServiceRequestBodyXml(root)
        return RouteServiceRequestXml(body)
    }

    override fun getDublinBusServiceStatus(): Single<ServiceStatusResponseXml> {
        return api.getServiceStatus(dublinBusServiceStatusKey)
    }

}
