package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.status.ServiceStatusResponseXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import io.reactivex.Single

interface DublinBusSoapResource {

    fun getDublinBusStops(): Single<StopsResponseXml>

    fun getDublinBusRoutes(): Single<RoutesResponseXml>

    fun getDublinBusLiveData(stopId: String): Single<LiveDataResponseXml>

    fun getDublinBusStopService(stopId: String): Single<StopServiceResponseXml>

    fun getDublinBusRouteService(routeId: String): Single<RouteServiceResponseXml>

    fun getDublinBusServiceStatus(): Single<ServiceStatusResponseXml>

}
