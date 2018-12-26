package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import io.reactivex.Single

interface DublinBusSoapResource {

    fun getDublinBusStops(): Single<StopsResponseXml>

    fun getDublinBusRoutes(): Single<RoutesResponseXml>

    fun getDublinBusLiveData(stopId: String): Single<LiveDataResponseXml>

    fun getDublinBusRouteService(routeId: String): Single<RouteServiceResponseXml>

}
