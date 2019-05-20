package ie.dublinbuspal.service

//class MockDublinBusApi : DublinBusApi {
//
//    override fun getStops(body: StopsRequestXml): Single<StopsResponseXml> {
//        return singleResponse("stops_response.xml", StopsResponseXml::class.java)
//    }
//
//    override fun getRoutes(body: RoutesRequestXml): Single<RoutesResponseXml> {
//        return singleResponse("routes_response.xml", RoutesResponseXml::class.java)
//    }
//
//    override fun getLiveData(body: LiveDataRequestXml): Single<LiveDataResponseXml> {
//        val stopId = body.liveDataRequestBodyXml.liveDataRequestRootXml.stopId
//        return singleResponse("live_data_response_$stopId.xml", LiveDataResponseXml::class.java)
//    }
//
//    override fun getStopService(body: StopServiceRequestXml): Single<StopServiceResponseXml> {
//        val stopId = body.stopServiceRequestBodyXml.stopServiceRequestRootXml.stopId
//        return singleResponse("stop_service_response_$stopId.xml", StopServiceResponseXml::class.java)
//    }
//
//    override fun getRouteService(body: RouteServiceRequestXml): Single<RouteServiceResponseXml> {
//        val routeId = body.routeServiceRequestBodyXml.routeServiceRequestRootXml.routeId.toLowerCase()
//        return singleResponse("route_service_response_$routeId}.xml", RouteServiceResponseXml::class.java)
//    }
//
//    override fun getServiceStatus(body: ServiceStatusRequestXml): Single<ServiceStatusResponseXml> {
//        return singleResponse("service_status_response.xml", ServiceStatusResponseXml::class.java)
//    }
//
//    private fun <T> singleResponse(fileName: String, type: Class<T>): Single<T> {
//        return Single.just(XmlUtils.deserialize(fileName, type))
//    }
//
//}
