package ie.dublinbuspal.service

import ie.dublinbuspal.service.api.DublinBusSoapApi
import ie.dublinbuspal.service.model.livedata.LiveDataRequestXml
import ie.dublinbuspal.service.model.livedata.LiveDataResponseXml
import ie.dublinbuspal.service.model.route.RoutesRequestXml
import ie.dublinbuspal.service.model.route.RoutesResponseXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceRequestXml
import ie.dublinbuspal.service.model.routeservice.RouteServiceResponseXml
import ie.dublinbuspal.service.model.status.ServiceStatusRequestXml
import ie.dublinbuspal.service.model.status.ServiceStatusResponseXml
import ie.dublinbuspal.service.model.stop.StopsRequestXml
import ie.dublinbuspal.service.model.stop.StopsResponseXml
import ie.dublinbuspal.service.model.stopservice.StopServiceRequestXml
import ie.dublinbuspal.service.model.stopservice.StopServiceResponseXml
import ie.dublinbuspal.service.util.XmlUtils
import io.reactivex.Single

class MockDublinBusSoapApi : DublinBusSoapApi {

    override fun getStops(body: StopsRequestXml): Single<StopsResponseXml> {
        return singleResponse("stops_response.xml", StopsResponseXml::class.java)
    }

    override fun getRoutes(body: RoutesRequestXml): Single<RoutesResponseXml> {
        return singleResponse("routes_response.xml", RoutesResponseXml::class.java)
    }

    override fun getLiveData(body: LiveDataRequestXml): Single<LiveDataResponseXml> {
        val stopId = body.liveDataRequestBodyXml.liveDataRequestRootXml.stopId
        return singleResponse("live_data_response_$stopId.xml", LiveDataResponseXml::class.java)
    }

    override fun getStopService(body: StopServiceRequestXml): Single<StopServiceResponseXml> {
        val stopId = body.stopServiceRequestBodyXml.stopServiceRequestRootXml.stopId
        return singleResponse("stop_service_response_$stopId.xml", StopServiceResponseXml::class.java)
    }

    override fun getRouteService(body: RouteServiceRequestXml): Single<RouteServiceResponseXml> {
        val routeId = body.routeServiceRequestBodyXml.routeServiceRequestRootXml.routeId.toLowerCase()
        return singleResponse("route_service_response_$routeId}.xml", RouteServiceResponseXml::class.java)
    }

    override fun getServiceStatus(body: ServiceStatusRequestXml): Single<ServiceStatusResponseXml> {
        return singleResponse("service_status_response.xml", ServiceStatusResponseXml::class.java)
    }

    private fun <T> singleResponse(fileName: String, type: Class<T>): Single<T> {
        return Single.just(XmlUtils.deserialize(fileName, type))
    }

}
