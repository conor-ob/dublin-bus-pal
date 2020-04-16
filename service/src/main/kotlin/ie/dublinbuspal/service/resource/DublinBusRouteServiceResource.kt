package ie.dublinbuspal.service.resource

import ie.dublinbuspal.service.api.RtpiRouteService
import ie.dublinbuspal.service.api.RtpiRouteServiceVariant
import ie.dublinbuspal.service.api.dublinbus.*
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.api.rtpi.RtpiRouteInformationJson
import ie.dublinbuspal.util.Operator
import io.reactivex.Single

class DublinBusRouteServiceResource(
    private val dublinBusApi: DublinBusApi,
    private val rtpiApi: RtpiApi,
    private val rtpiFallbackApi: RtpiApi
) {

    fun getRouteService(routeId: String, operatorId: String): Single<RtpiRouteService> {
        if (operatorId.equals(Operator.DUBLIN_BUS.code, ignoreCase = true)) {
            return fetchDublinBusRouteServices(routeId).map { dublinBusRouteService ->
                val variants = mutableListOf<RtpiRouteServiceVariant>()
                if (dublinBusRouteService.inboundStopXmls.isNotEmpty()) {
                    variants.add(
                            RtpiRouteServiceVariant(
                                    origin = dublinBusRouteService.dublinBusStopDataByRoute!!.destination!!,
                                    destination = dublinBusRouteService.dublinBusStopDataByRoute!!.origin!!,
                                    stopIds = dublinBusRouteService.inboundStopXmls.map { it.id!! }
                            )
                    )
                }
                if (dublinBusRouteService.outboundStopXmls.isNotEmpty()) {
                    variants.add(
                            RtpiRouteServiceVariant(
                                    origin = dublinBusRouteService.dublinBusStopDataByRoute!!.origin!!,
                                    destination = dublinBusRouteService.dublinBusStopDataByRoute!!.destination!!,
                                    stopIds = dublinBusRouteService.outboundStopXmls.map { it.id!! }
                            )
                    )
                }
                RtpiRouteService(routeId, operatorId, variants)
            }
        } else if (operatorId.equals(Operator.GO_AHEAD.code, ignoreCase = true)) {
            return fetchRtpiRouteServices(routeId, operatorId).map { rtpiRouteServices ->
                val variants = mutableListOf<RtpiRouteServiceVariant>()
                for (rtpiRouteService in rtpiRouteServices) {
                    variants.add(
                            RtpiRouteServiceVariant(
                                    origin = rtpiRouteService.origin!!,
                                    destination = rtpiRouteService.destination!!,
                                    stopIds = rtpiRouteService.stops.map { it.displayId!! }
                            )
                    )
                }
                RtpiRouteService(
                        routeId = routeId,
                        operatorId = operatorId,
                        variants = variants
                )
            }
        }
        throw IllegalStateException("Unable to get Route Service for route: '$routeId' and operator: '$operatorId'")
    }

    private fun fetchDublinBusRouteServices(routeId: String): Single<DublinBusStopDataByRouteResponseXml> {
        val requestRoot = DublinBusStopDataByRouteRequestRootXml(routeId)
        val requestBody = DublinBusStopDataByRouteRequestBodyXml(requestRoot)
        val request = DublinBusStopDataByRouteRequestXml(requestBody)
        return dublinBusApi.getStopDataByRoute(request)
    }

    private fun fetchRtpiRouteServices(routeId: String, operatorId: String): Single<List<RtpiRouteInformationJson>> {
        return rtpiApi.routeInformation(routeId, operatorId, RtpiApi.JSON).map { it.results }
            .onErrorResumeNext { rtpiFallbackApi.routeInformation(routeId, operatorId, RtpiApi.JSON).map { it.results } }
    }

}
